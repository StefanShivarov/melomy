package softuni.javaweb.melomy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.binding.CommentAddBindingModel;
import softuni.javaweb.melomy.model.view.AlbumViewModel;
import softuni.javaweb.melomy.model.view.SongViewModel;
import softuni.javaweb.melomy.service.SongService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
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

    @ModelAttribute("searchResults")
    public List<SongViewModel> searchResults(){
        return new ArrayList<>();
    }

    @ModelAttribute("commentAddBindingModel")
    public CommentAddBindingModel commentAddBindingModel(){
        return new CommentAddBindingModel();
    }
}
