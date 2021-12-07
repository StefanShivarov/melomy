package softuni.javaweb.melomy.service.impl;

import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.GenreEntity;
import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;
import softuni.javaweb.melomy.repository.GenreRepository;
import softuni.javaweb.melomy.service.GenreService;

import java.util.Arrays;

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
}
