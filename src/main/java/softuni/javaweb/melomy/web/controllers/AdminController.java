package softuni.javaweb.melomy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.binding.AlbumAddBindingModel;
import softuni.javaweb.melomy.model.binding.ArtistAddBindingModel;
import softuni.javaweb.melomy.model.binding.SongAddBindingModel;
import softuni.javaweb.melomy.model.service.AlbumServiceModel;
import softuni.javaweb.melomy.model.service.ArtistServiceModel;
import softuni.javaweb.melomy.model.service.SongServiceModel;
import softuni.javaweb.melomy.model.view.*;
import softuni.javaweb.melomy.service.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ArtistService artistService;
    private final GenreService genreService;
    private final AlbumService albumService;
    private final SongService songService;
    private final UserService userService;
    private final RequestCounterService requestCounterService;

    public AdminController(ArtistService artistService, GenreService genreService, AlbumService albumService, SongService songService, UserService userService, RequestCounterService requestCounterService) {
        this.artistService = artistService;
        this.genreService = genreService;
        this.albumService = albumService;
        this.songService = songService;
        this.userService = userService;
        this.requestCounterService = requestCounterService;
    }

    @GetMapping
    public String adminPage(){
        return "admin";
    }

    @GetMapping("/statistics")
    public ModelAndView statisticsPage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("requestCounterStats", requestCounterService.getRequestCounterStats());
        modelAndView.setViewName("stats");

        return modelAndView;
    }

    @GetMapping("/songs/add")
    public String addSong(){
        return "add-song";
    }

    @PostMapping("/songs/add")
    public String addSongConfirm(@Valid SongAddBindingModel songAddBindingModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("songAddBindingModel", songAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.songAddBindingModel", bindingResult);

            return "redirect:/admin/songs/add";
        }

        SongServiceModel songServiceModel = songService.addSong(songAddBindingModel);
        return "redirect:/songs/"+songServiceModel.getId()+"/details";
    }

    @GetMapping("/albums/add")
    public String addAlbum(){
        return "add-album";
    }

    @PostMapping("/albums/add")
    public String addAlbumConfirm(@Valid AlbumAddBindingModel albumAddBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("albumAddBindingModel", albumAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.albumAddBindingModel", bindingResult);

            return "redirect:/admin/albums/add";
        }

        AlbumServiceModel albumServiceModel = albumService.addAlbum(albumAddBindingModel);
        return "redirect:/albums/"+albumServiceModel.getId()+"/details";

    }

    @GetMapping("/artists/add")
    public String addArtist(){
        return "add-artist";
    }

    @PostMapping("/artists/add")
    public String addArtistConfirm(@Valid ArtistAddBindingModel artistAddBindingModel,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes){


        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("artistAddBindindModel", artistAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.artistAddBindingModel", bindingResult);

            return "redirect:/admin/artists/add";
        }

        ArtistServiceModel artistServiceModel = artistService.addArtist(artistAddBindingModel);
        return "redirect:/artists/"+artistServiceModel.getId()+"/details";
    }

    @GetMapping("/users/manage")
    public String manageUsers(){
        return "search-users";
    }

    @PostMapping("/users/manage")
    public String searchUsers(@RequestParam(name = "input") String input, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("searchResults", userService.searchByUsernameContaining(input));
        return "redirect:/admin/users/manage";
    }

    @ModelAttribute("searchResults")
    public List<UserViewModel> searchResults(){
        return userService.searchByUsernameContaining("");
    }

    @ModelAttribute("artistAddBindingModel")
    public ArtistAddBindingModel artistAddBindingModel(){
        return new ArtistAddBindingModel();
    }

    @ModelAttribute("albumAddBindingModel")
    public AlbumAddBindingModel albumAddBindingModel(){
        return new AlbumAddBindingModel();
    }

    @ModelAttribute("songAddBindingModel")
    public SongAddBindingModel songAddBindingModel(){
        return new SongAddBindingModel();
    }

    @ModelAttribute("allGenres")
    public List<GenreViewModel> allGenres(){
        return genreService.findAllGenres();
    }

    @ModelAttribute("allArtists")
    public List<ArtistViewModel> allArtists(){
        return artistService.findAllArtists();
    }

    @ModelAttribute("allAlbums")
    public List<AlbumViewModel> allAlbums(){
        return albumService.findAllAlbums();
    }

}
