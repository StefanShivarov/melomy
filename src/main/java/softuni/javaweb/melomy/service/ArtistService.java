package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.binding.ArtistAddBindingModel;
import softuni.javaweb.melomy.model.service.ArtistServiceModel;
import softuni.javaweb.melomy.model.view.ArtistViewModel;

public interface ArtistService {

    ArtistServiceModel addArtist(ArtistAddBindingModel artistAddBindingModel);

    ArtistViewModel findById(Long id);
}
