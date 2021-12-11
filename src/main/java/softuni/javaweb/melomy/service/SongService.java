package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.binding.SongAddBindingModel;
import softuni.javaweb.melomy.model.service.SongServiceModel;
import softuni.javaweb.melomy.model.view.SongViewModel;

import java.util.List;

public interface SongService {

    SongServiceModel addSong(SongAddBindingModel songAddBindingModel);

    SongViewModel findById(Long id);

    List<SongViewModel> findSongsByAlbum(Long albumId);

    List<SongViewModel> searchByNameContaining(String input);

    void deleteSong(Long id);
}
