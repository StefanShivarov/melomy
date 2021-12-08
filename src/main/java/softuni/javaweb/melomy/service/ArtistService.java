package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.binding.ArtistAddBindingModel;
import softuni.javaweb.melomy.model.entity.ArtistEntity;
import softuni.javaweb.melomy.model.service.ArtistServiceModel;
import softuni.javaweb.melomy.model.view.ArtistViewModel;

import java.util.List;

public interface ArtistService {

    ArtistServiceModel addArtist(ArtistAddBindingModel artistAddBindingModel);

    ArtistViewModel findById(Long id);

    List<ArtistViewModel> searchByNameContaining(String input);

    List<ArtistViewModel> findAllArtists();

    ArtistEntity getArtistById(Long id);

}
