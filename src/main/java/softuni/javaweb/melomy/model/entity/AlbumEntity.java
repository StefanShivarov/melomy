package softuni.javaweb.melomy.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "albums")
public class AlbumEntity extends BaseEntity{

    private String name;
    private String imageUrl;
    private String description;
    private Integer year;
//    private List<SongEntity> songs;
    private ArtistEntity artist;

    public AlbumEntity(){

    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public AlbumEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public AlbumEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Column(name = "year", nullable = false)
    public Integer getYear() {
        return year;
    }

    public AlbumEntity setYear(Integer year) {
        this.year = year;
        return this;
    }
//    @OneToMany
//    public List<SongEntity> getSongs() {
//        return songs;
//    }
//
//    public AlbumEntity setSongs(List<SongEntity> songs) {
//        this.songs = songs;
//        return this;
//    }

    @ManyToOne
    public ArtistEntity getArtist() {
        return artist;
    }

    public AlbumEntity setArtist(ArtistEntity artist) {
        this.artist = artist;
        return this;
    }

    @Column(name = "description", columnDefinition = "LONGTEXT")
    public String getDescription() {
        return description;
    }

    public AlbumEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
