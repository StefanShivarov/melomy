package softuni.javaweb.melomy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.binding.AlbumAddBindingModel;
import softuni.javaweb.melomy.model.binding.ArtistAddBindingModel;
import softuni.javaweb.melomy.model.binding.SongAddBindingModel;
import softuni.javaweb.melomy.model.service.ArtistServiceModel;
import softuni.javaweb.melomy.service.ArtistService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ArtistService artistService;

    public AdminController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public String adminPage(){
        return "admin";
    }

    @GetMapping("/songs/add")
    public String addSong(){
        return "add-song";
    }

    @GetMapping("/albums/add")
    public String addAlbum(){
        return "add-album";
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

            return "redirect:/artists/add";
        }

        ArtistServiceModel artistServiceModel = artistService.addArtist(artistAddBindingModel);
        return "redirect:/artists/"+artistServiceModel.getId()+"/details";
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
}
