package softuni.javaweb.melomy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.javaweb.melomy.service.ArtistService;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{id}/details")
    public String artistDetails(@PathVariable Long id, Model model){
        model.addAttribute("artistViewModel", artistService.findById(id));

        return "artist";
    }
}
