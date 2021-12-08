package softuni.javaweb.melomy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.view.ArtistViewModel;
import softuni.javaweb.melomy.service.AlbumService;
import softuni.javaweb.melomy.service.ArtistService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final AlbumService albumService;

    public ArtistController(ArtistService artistService, AlbumService albumService) {
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @GetMapping("/{id}/details")
    public String artistDetails(@PathVariable Long id, Model model){
        model.addAttribute("artistViewModel", artistService.findById(id));
        model.addAttribute("albums", albumService.findAllAlbumsByArtist(id));
        return "artist";
    }

    @GetMapping("/search")
    public String searchArtists(){

        return "search-artists";
    }

    @PostMapping("/search")
    public String searchArtistsConfirm(Model model, @RequestParam(name = "input") String input, RedirectAttributes redirectAttributes){

//        if(model.containsAttribute("searchResults")){
//            model.addAttribute("searchResults", null);
//            model.addAttribute("noResults", true);
//        }
        redirectAttributes.addFlashAttribute("searchResults", artistService.searchByNameContaining(input));
        redirectAttributes.addFlashAttribute("noResults", artistService.searchByNameContaining(input).size() == 0);
        return "redirect:/artists/search";
    }

    @ModelAttribute("searchResults")
    public List<ArtistViewModel> searchResults(){
        return new ArrayList<>();
    }


}
