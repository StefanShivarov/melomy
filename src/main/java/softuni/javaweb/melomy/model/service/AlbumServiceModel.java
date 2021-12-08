package softuni.javaweb.melomy.model.service;

import softuni.javaweb.melomy.model.entity.ArtistEntity;
import softuni.javaweb.melomy.model.entity.SongEntity;

import java.util.List;

public class AlbumServiceModel {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private Integer year;
    private Long artistId;

    public Long getId() {
        return id;
    }

    public AlbumServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AlbumServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AlbumServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public AlbumServiceModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AlbumServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public AlbumServiceModel setArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }
}
