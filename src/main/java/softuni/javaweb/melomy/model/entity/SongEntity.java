package softuni.javaweb.melomy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "songs")
public class SongEntity extends BaseEntity{

    private String name;
    private String songUrl;
    private ArtistEntity artist;
    private AlbumEntity album;

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
}
