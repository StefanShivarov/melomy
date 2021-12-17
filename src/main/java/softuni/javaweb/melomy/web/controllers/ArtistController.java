package softuni.javaweb.melomy.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.view.ArtistViewModel;
import softuni.javaweb.melomy.service.AlbumService;
import softuni.javaweb.melomy.service.ArtistService;
import softuni.javaweb.melomy.service.SongService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, AlbumService albumService, SongService songService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.songService = songService;
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
    public String searchArtistsConfirm(@RequestParam(name = "input") String input, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("searchResults", artistService.searchByNameContaining(input));
        return "redirect:/artists/search";
    }

    @PreAuthorize("#userServiceImpl.isAdmin(#principal.username)")
    @DeleteMapping("/{id}/delete")
    public String deleteSong(@PathVariable(name = "id") Long id, @AuthenticationPrincipal UserDetails principal){

        songService.deleteAllSongsByArtist(id);
        artistService.deleteArtist(id);
        return "redirect:/artists/search";
    }

    @ModelAttribute("searchResults")
    public List<ArtistViewModel> searchResults(){
        return new ArrayList<>();
    }


}
