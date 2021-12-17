package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.binding.ArtistAddBindingModel;
import softuni.javaweb.melomy.model.entity.ArtistEntity;
import softuni.javaweb.melomy.model.service.ArtistServiceModel;
import softuni.javaweb.melomy.model.view.ArtistViewModel;
import softuni.javaweb.melomy.repository.ArtistRepository;
import softuni.javaweb.melomy.repository.GenreRepository;
import softuni.javaweb.melomy.service.AlbumService;
import softuni.javaweb.melomy.service.ArtistService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ModelMapper modelMapper;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final AlbumService albumService;

    public ArtistServiceImpl(ModelMapper modelMapper, ArtistRepository artistRepository, GenreRepository genreRepository, AlbumService albumService) {
        this.modelMapper = modelMapper;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.albumService = albumService;
    }

    @Override
    public ArtistServiceModel addArtist(ArtistAddBindingModel artistAddBindingModel) {

        ArtistServiceModel artistServiceModel = modelMapper.map(artistAddBindingModel, ArtistServiceModel.class);
        ArtistEntity artistEntity = modelMapper.map(artistServiceModel, ArtistEntity.class)
                .setGenre(genreRepository.findByName(artistServiceModel.getGenre()));


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

    @Override
    public List<ArtistViewModel> searchByNameContaining(String input) {
        return artistRepository
                .findAllByNameContaining(input)
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArtistViewModel> findAllArtists() {
        return artistRepository
                .findAll()
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistEntity getArtistById(Long id) {
        return artistRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public void deleteArtist(Long id) {

        albumService
                .findAllAlbumsByArtist(id)
                .forEach(albumViewModel -> albumService.deleteAlbum(albumViewModel.getId()));

        artistRepository.deleteById(id);
    }


    private ArtistViewModel mapToViewModel(ArtistEntity artistEntity){
        ArtistViewModel artistViewModel = modelMapper.map(artistEntity, ArtistViewModel.class)
                .setGenre(artistEntity.getGenre().getName().name());

        return artistViewModel;
    }
}
