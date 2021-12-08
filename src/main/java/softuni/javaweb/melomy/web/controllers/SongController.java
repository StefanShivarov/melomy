package softuni.javaweb.melomy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.javaweb.melomy.model.view.AlbumViewModel;
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

    @GetMapping("/{id}/details")
    public String songDetails(@PathVariable(name = "id") Long id, Model model){

        model.addAttribute("songViewModel", songService.findById(id));

        return "song";
    }

    @ModelAttribute("searchResults")
    public List<AlbumViewModel> searchResults(){
        return new ArrayList<>();
    }
}
