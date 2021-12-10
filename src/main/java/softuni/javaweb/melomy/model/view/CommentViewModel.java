package softuni.javaweb.melomy.model.view;

import java.time.LocalDateTime;

public class CommentViewModel {

    private Long id;
    private String message;
    private String authorName;
    private String created;

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommentViewModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public CommentViewModel setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public CommentViewModel setCreated(String created) {
        this.created = created;
        return this;
    }
}
