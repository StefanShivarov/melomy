package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.binding.SongAddBindingModel;
import softuni.javaweb.melomy.model.entity.SongEntity;
import softuni.javaweb.melomy.model.service.SongServiceModel;
import softuni.javaweb.melomy.model.view.SongViewModel;
import softuni.javaweb.melomy.repository.SongRepository;
import softuni.javaweb.melomy.service.AlbumService;
import softuni.javaweb.melomy.service.ArtistService;
import softuni.javaweb.melomy.service.CommentService;
import softuni.javaweb.melomy.service.SongService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    private final ModelMapper modelMapper;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongRepository songRepository;
    private final CommentService commentService;


    public SongServiceImpl(ModelMapper modelMapper, AlbumService albumService, SongRepository songRepository, ArtistService artistService, CommentService commentService) {
        this.modelMapper = modelMapper;
        this.albumService = albumService;
        this.artistService = artistService;
        this.songRepository = songRepository;
        this.commentService = commentService;
    }

    @Override
    public SongServiceModel addSong(SongAddBindingModel songAddBindingModel) {
        SongServiceModel songServiceModel = modelMapper.map(songAddBindingModel, SongServiceModel.class)
                .setId(null)
                .setArtistId(albumService.findById(songAddBindingModel.getAlbumId()).getArtistId());

        SongEntity songEntity = modelMapper.map(songServiceModel, SongEntity.class)
                .setAlbum(albumService.getAlbumById(songServiceModel.getAlbumId()))
                .setArtist(artistService.getArtistById(songServiceModel.getArtistId()));

        SongEntity savedEntity = songRepository.save(songEntity);
        return modelMapper.map(savedEntity, SongServiceModel.class);
    }

    @Override
    public SongViewModel findById(Long id) {
        return songRepository
                .findById(id)
                .map(this::mapToViewModel)
                .get();
    }

    @Override
    public List<SongViewModel> findSongsByAlbum(Long id) {
        return songRepository
                .findAllByAlbum_Id(id)
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<SongViewModel> searchByNameContaining(String input) {
        return songRepository
                .findAllByNameContaining(input)
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSong(Long id) {

        commentService.deleteAllCommentsBySong(id);
        songRepository.deleteById(id);
    }


    private SongViewModel mapToViewModel(SongEntity songEntity){
        return modelMapper.map(songEntity, SongViewModel.class)
                .setArtistId(songEntity.getArtist().getId())
                .setAlbumId(songEntity.getAlbum().getId())
                .setArtistName(songEntity.getArtist().getName())
                .setAlbumName(songEntity.getAlbum().getName())
                .setImageUrl(songEntity.getAlbum().getImageUrl());

    }
}
