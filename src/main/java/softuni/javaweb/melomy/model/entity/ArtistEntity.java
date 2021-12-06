package softuni.javaweb.melomy.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="artists")
public class ArtistEntity extends BaseEntity{

    private String name;
    private String imageUrl;
    private List<SongEntity> songs;
    private List<AlbumEntity> albums;
    private List<GenreEntity> genres;

    public ArtistEntity(){

    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public ArtistEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public ArtistEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @OneToMany(mappedBy = "artist")
    public List<SongEntity> getSongs() {
        return songs;
    }

    public ArtistEntity setSongs(List<SongEntity> songs) {
        this.songs = songs;
        return this;
    }

    @OneToMany(mappedBy = "artist")
    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public ArtistEntity setAlbums(List<AlbumEntity> albums) {
        this.albums = albums;
        return this;
    }

    @ManyToMany(mappedBy = "artists")
    public List<GenreEntity> getGenres() {
        return genres;
    }

    public ArtistEntity setGenres(List<GenreEntity> genres) {
        this.genres = genres;
        return this;
    }
}
