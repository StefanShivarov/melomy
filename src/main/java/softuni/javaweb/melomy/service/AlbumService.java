package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.binding.AlbumAddBindingModel;
import softuni.javaweb.melomy.model.entity.AlbumEntity;
import softuni.javaweb.melomy.model.service.AlbumServiceModel;
import softuni.javaweb.melomy.model.view.AlbumViewModel;

import java.util.List;

public interface AlbumService {

    AlbumViewModel mapToViewModel(AlbumEntity albumEntity);

    List<AlbumViewModel> searchByNameContaining(String input);

    AlbumServiceModel addAlbum(AlbumAddBindingModel albumAddBindingModel);

    AlbumViewModel findById(Long id);

    List<AlbumViewModel> findAllAlbumsByArtist(Long artistId);

    List<AlbumViewModel> findAllAlbums();

    AlbumEntity getAlbumById(Long id);

    void deleteAlbum(Long id);
}
