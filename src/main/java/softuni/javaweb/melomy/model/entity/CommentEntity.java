package softuni.javaweb.melomy.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    private String content;
    private LocalDateTime created;
    private SongEntity song;
    private UserEntity author;

    @Lob
    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    public CommentEntity setContent(String content) {
        this.content = content;
        return this;
    }

    @Column(name = "created", nullable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    public CommentEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    @ManyToOne
    public SongEntity getSong() {
        return song;
    }

    public CommentEntity setSong(SongEntity song) {
        this.song = song;
        return this;
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }
}
