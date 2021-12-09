package softuni.javaweb.melomy.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "songs")
public class SongEntity extends BaseEntity{

    private String name;
    private String songUrl;
    private ArtistEntity artist;
    private AlbumEntity album;
    private List<CommentEntity> comments;

    public SongEntity(){

    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public SongEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "song_url", nullable = false)
    public String getSongUrl() {
        return songUrl;
    }

    public SongEntity setSongUrl(String songUrl) {
        this.songUrl = songUrl;
        return this;
    }

    @ManyToOne
    public ArtistEntity getArtist() {
        return artist;
    }

    public SongEntity setArtist(ArtistEntity artist) {
        this.artist = artist;
        return this;
    }

    @ManyToOne
    public AlbumEntity getAlbum() {
        return album;
    }

    public SongEntity setAlbum(AlbumEntity album) {
        this.album = album;
        return this;
    }

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    public List<CommentEntity> getComments() {
        return comments;
    }

    public SongEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }
}
