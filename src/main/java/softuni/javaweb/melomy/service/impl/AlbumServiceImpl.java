package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.binding.AlbumAddBindingModel;
import softuni.javaweb.melomy.model.entity.AlbumEntity;
import softuni.javaweb.melomy.model.entity.ArtistEntity;
import softuni.javaweb.melomy.model.service.AlbumServiceModel;
import softuni.javaweb.melomy.model.view.AlbumViewModel;
import softuni.javaweb.melomy.model.view.ArtistViewModel;
import softuni.javaweb.melomy.repository.AlbumRepository;
import softuni.javaweb.melomy.repository.ArtistRepository;
import softuni.javaweb.melomy.service.AlbumService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public AlbumServiceImpl(ModelMapper modelMapper, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public AlbumViewModel mapToViewModel(AlbumEntity albumEntity){

        return modelMapper.map(albumEntity, AlbumViewModel.class)
                .setArtistName(albumEntity.getArtist().getName())
                .setArtistId(albumEntity.getArtist().getId());
    }

    @Override
    public List<AlbumViewModel> searchByNameContaining(String input) {
        return albumRepository
                .findAllByNameContaining(input)
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumServiceModel addAlbum(AlbumAddBindingModel albumAddBindingModel) {
        AlbumServiceModel albumServiceModel = modelMapper.map(albumAddBindingModel, AlbumServiceModel.class)
                .setId(null);
        AlbumEntity albumEntity = modelMapper.map(albumServiceModel, AlbumEntity.class)
                .setArtist(artistRepository.getById(albumServiceModel.getArtistId()));


        AlbumEntity savedEntity = albumRepository.save(albumEntity);

       return modelMapper.map(savedEntity, AlbumServiceModel.class);
    }

    @Override
    public AlbumViewModel findById(Long id) {

        return albumRepository
                .findById(id)
                .map(this::mapToViewModel)
                .get();
    }

    @Override
    public List<AlbumViewModel> findAllAlbumsByArtist(Long artistId) {

        return albumRepository
                .findAllByArtistOrderByYearDesc(artistRepository.getById(artistId))
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumViewModel> findAllAlbums() {
        return albumRepository
                .findAll()
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumEntity getAlbumById(Long id) {
        return albumRepository.findById(id)
                .orElse(null);
    }


}
