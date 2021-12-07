package softuni.javaweb.melomy.model.service;

import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;

public class ArtistServiceModel {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private GenreNameEnum genre;

    public ArtistServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ArtistServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArtistServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArtistServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public GenreNameEnum getGenre() {
        return genre;
    }

    public ArtistServiceModel setGenre(GenreNameEnum genre) {
        this.genre = genre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
