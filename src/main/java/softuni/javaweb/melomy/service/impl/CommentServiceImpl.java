package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.CommentEntity;
import softuni.javaweb.melomy.model.entity.SongEntity;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.service.CommentServiceModel;
import softuni.javaweb.melomy.model.view.CommentViewModel;
import softuni.javaweb.melomy.repository.CommentRepository;
import softuni.javaweb.melomy.repository.SongRepository;
import softuni.javaweb.melomy.repository.UserRepository;
import softuni.javaweb.melomy.service.CommentService;
import softuni.javaweb.melomy.web.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, SongRepository songRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<CommentViewModel> getCommentsBySong(Long songId) {

        var songOpt = songRepository
                .findById(songId);

        if(songOpt.isEmpty()){
            throw new ObjectNotFoundException("Song with id "+songId+" was not found!");
        }

        return songOpt
                .get()
                .getComments()
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {

        SongEntity songEntity = songRepository
                .findById(commentServiceModel.getSongId())
                .orElseThrow(()->new ObjectNotFoundException("Song with id "+commentServiceModel.getSongId()+" not found!"));

        UserEntity userEntity = userRepository.
                findByUsername(commentServiceModel.getAuthorName())
                .orElseThrow(()-> new ObjectNotFoundException("User with username "+commentServiceModel.getAuthorName()+" not found!"));

        CommentEntity commentEntity = new CommentEntity()
                .setContent(commentServiceModel.getMessage())
                .setCreated(LocalDateTime.now())
                .setAuthor(userEntity)
                .setSong(songEntity);

        CommentEntity savedComment = commentRepository.save(commentEntity);
        return mapToViewModel(savedComment);
    }

    @Transactional
    @Override
    public void deleteAllCommentsBySong(Long songId) {

        SongEntity songEntity = songRepository.getById(songId);
        commentRepository.deleteAllBySong(songEntity);
    }

    private CommentViewModel mapToViewModel(CommentEntity commentEntity){

        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel
                .setId(commentEntity.getId())
                .setCreated(commentEntity.getCreated().format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss")))
                .setMessage(commentEntity.getContent())
                .setAuthorName(commentEntity.getAuthor().getUsername());

        return commentViewModel;
    }
}
