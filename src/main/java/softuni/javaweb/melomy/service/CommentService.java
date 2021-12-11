package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.service.CommentServiceModel;
import softuni.javaweb.melomy.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    List<CommentViewModel> getCommentsBySong(Long songId);

    CommentViewModel createComment(CommentServiceModel commentServiceModel);

    void deleteAllCommentsBySong(Long songId);
}
