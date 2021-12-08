package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.view.GenreViewModel;

import java.util.List;

public interface GenreService {

    void initializeGenres();

    List<GenreViewModel> findAllGenres();
}
