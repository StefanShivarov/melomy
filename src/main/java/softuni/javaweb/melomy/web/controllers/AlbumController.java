package softuni.javaweb.melomy.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.view.AlbumViewModel;
import softuni.javaweb.melomy.service.AlbumService;
import softuni.javaweb.melomy.service.SongService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final SongService songService;

    public AlbumController(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping("/search")
    public String searchAlbums(){
        return "search-albums";
    }

    @PostMapping("/search")
    public String searchAlbumsConfirm(@RequestParam(name = "input") String input, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("searchResults", albumService.searchByNameContaining(input));
        return "redirect:/albums/search";
    }

    @GetMapping("/{id}/details")
    public String albumDetails(@PathVariable(name = "id") Long id, Model model){
        model.addAttribute("albumViewModel", albumService.findById(id));
        model.addAttribute("songs", songService.findSongsByAlbum(id));
        return "album";
    }

    @PreAuthorize("#userServiceImpl.isAdmin(#principal.username)")
    @DeleteMapping("/{id}/delete")
    public String deleteSong(@PathVariable(name = "id") Long id, @AuthenticationPrincipal UserDetails principal){

        songService
                .findSongsByAlbum(id)
                .forEach(songViewModel -> songService.deleteSong(songViewModel.getId()));
        albumService.deleteAlbum(id);
        return "redirect:/albums/search";
    }

    @ModelAttribute("searchResults")
    public List<AlbumViewModel> searchResults(){
        return new ArrayList<>();
    }
}
