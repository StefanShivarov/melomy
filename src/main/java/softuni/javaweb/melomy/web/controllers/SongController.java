package softuni.javaweb.melomy.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.binding.CommentAddBindingModel;
import softuni.javaweb.melomy.model.view.SongViewModel;
import softuni.javaweb.melomy.service.SongService;
import softuni.javaweb.melomy.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;
    private final UserService userService;

    public SongController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public String searchSongs(){
        return "search-songs";
    }

    @PostMapping("/search")
    public String searchAlbumsConfirm(@RequestParam(name = "input") String input, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("searchResults", songService.searchByNameContaining(input));
        return "redirect:/songs/search";
    }

    @GetMapping("/{id}/details")
    public String songDetails(@PathVariable(name = "id") Long id, Model model){

        model.addAttribute("songViewModel", songService.findById(id));

        return "song";
    }

    @PreAuthorize("#userServiceImpl.isAdmin(#principal.username)")
    @DeleteMapping("/{id}/delete")
    public String deleteSong(@PathVariable(name = "id") Long id, @AuthenticationPrincipal UserDetails principal){
//        if(!userService.isAdmin(principal.getUsername())){
//            throw new RuntimeException();
//        }
        songService.deleteSong(id);
        return "redirect:/songs/search";
    }


    @ModelAttribute("searchResults")
    public List<SongViewModel> searchResults(){
        return new ArrayList<>();
    }

    @ModelAttribute("commentAddBindingModel")
    public CommentAddBindingModel commentAddBindingModel(){
        return new CommentAddBindingModel();
    }
}
