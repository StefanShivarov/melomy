package softuni.javaweb.melomy.model.service;

public class CommentServiceModel {

    private Long songId;
    private String message;
    private String creator;

    public Long getSongId() {
        return songId;
    }

    public CommentServiceModel setSongId(Long songId) {
        this.songId = songId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommentServiceModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public CommentServiceModel setCreator(String creator) {
        this.creator = creator;
        return this;
    }
}
