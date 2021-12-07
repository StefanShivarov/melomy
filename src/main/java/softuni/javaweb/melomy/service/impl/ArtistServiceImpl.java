package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.binding.ArtistAddBindingModel;
import softuni.javaweb.melomy.model.entity.ArtistEntity;
import softuni.javaweb.melomy.model.service.ArtistServiceModel;
import softuni.javaweb.melomy.model.view.ArtistViewModel;
import softuni.javaweb.melomy.repository.ArtistRepository;
import softuni.javaweb.melomy.repository.GenreRepository;
import softuni.javaweb.melomy.service.ArtistService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ModelMapper modelMapper;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    public ArtistServiceImpl(ModelMapper modelMapper, ArtistRepository artistRepository, GenreRepository genreRepository) {
        this.modelMapper = modelMapper;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public ArtistServiceModel addArtist(ArtistAddBindingModel artistAddBindingModel) {

        ArtistServiceModel artistServiceModel = modelMapper.map(artistAddBindingModel, ArtistServiceModel.class);
        ArtistEntity artistEntity = modelMapper.map(artistServiceModel, ArtistEntity.class)
                .setGenres(List.of(genreRepository.findByName(artistServiceModel.getGenre())))
                .setAlbums(new ArrayList<>())
                .setSongs(new ArrayList<>());

        ArtistEntity savedEntity = artistRepository.save(artistEntity);
        return modelMapper.map(savedEntity, ArtistServiceModel.class);
    }

    @Override
    public ArtistViewModel findById(Long id) {
        ArtistViewModel artistViewModel = artistRepository
                .findById(id)
                .map(this::mapToViewModel)
                .get();

        return artistViewModel;
    }

    private ArtistViewModel mapToViewModel(ArtistEntity artistEntity){
        ArtistViewModel artistViewModel = modelMapper.map(artistEntity, ArtistViewModel.class)
                .setGenre(artistEntity.getGenres().get(0).getName().name());

        return artistViewModel;
    }
}
