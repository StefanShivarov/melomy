package softuni.javaweb.melomy.service.impl;

import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.GenreEntity;
import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;
import softuni.javaweb.melomy.model.view.GenreViewModel;
import softuni.javaweb.melomy.repository.GenreRepository;
import softuni.javaweb.melomy.service.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void initializeGenres() {
        if(genreRepository.count()==0){
            Arrays.stream(GenreNameEnum.values())
                    .forEach(genreNameEnum -> genreRepository.save(new GenreEntity(genreNameEnum)));
        }
    }

    @Override
    public List<GenreViewModel> findAllGenres() {

        return genreRepository
                .findAll()
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    private GenreViewModel mapToViewModel(GenreEntity genreEntity){
        return new GenreViewModel()
                .setName(genreEntity.getName().name());
    }
}
