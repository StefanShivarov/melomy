package softuni.javaweb.melomy.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import softuni.javaweb.melomy.model.binding.CommentAddBindingModel;
import softuni.javaweb.melomy.model.service.CommentServiceModel;
import softuni.javaweb.melomy.model.validation.ApiError;
import softuni.javaweb.melomy.model.view.CommentViewModel;
import softuni.javaweb.melomy.service.CommentService;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentRestController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/{songId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(
            @PathVariable(name = "songId") Long songId,
            Principal principal){

        return ResponseEntity.ok(commentService.getCommentsBySong(songId));
    }

    @PostMapping("/api/{songId}/comments")
    public ResponseEntity<CommentViewModel> addComment(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable(name = "songId") Long songId,
            @RequestBody @Valid CommentAddBindingModel commentAddBindingModel){

       CommentServiceModel commentServiceModel = modelMapper.map(commentAddBindingModel, CommentServiceModel.class);
       commentServiceModel.setSongId(songId)
               .setAuthorName(principal.getUsername());
       CommentViewModel commentViewModel = commentService.createComment(commentServiceModel);

       URI locationOfNewComment = URI.create(String.format("/api/%s/comments/%s", songId, commentViewModel.getId()));

       return ResponseEntity.created(locationOfNewComment)
               .body(commentViewModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exception){

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exception.getFieldErrors().forEach(fieldError -> apiError.addFieldWithError(fieldError.getField()));

        return ResponseEntity.badRequest().body(apiError);
    }


}
