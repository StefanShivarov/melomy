package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.CommentEntity;
import softuni.javaweb.melomy.model.service.CommentServiceModel;
import softuni.javaweb.melomy.model.view.CommentViewModel;
import softuni.javaweb.melomy.repository.CommentRepository;
import softuni.javaweb.melomy.repository.SongRepository;
import softuni.javaweb.melomy.service.CommentService;
import softuni.javaweb.melomy.web.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final SongRepository songRepository;

    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, SongRepository songRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.songRepository = songRepository;
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

        //TODO implement addCommentLogic
        return null;
    }

    private CommentViewModel mapToViewModel(CommentEntity commentEntity){

        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel
                .setId(commentEntity.getId())
                .setCreated(commentEntity.getCreated())
                .setMessage(commentEntity.getContent())
                .setAuthorName(commentEntity.getAuthor().getUsername());

        return commentViewModel;
    }
}
