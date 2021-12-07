package softuni.javaweb.melomy.model.binding;

import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;

import javax.validation.constraints.NotBlank;

public class ArtistAddBindingModel {

    private String name;
    private String description;
    private String imageUrl;
    private GenreNameEnum genre;

    public ArtistAddBindingModel() {
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public ArtistAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotBlank
    public String getImageUrl() {
        return imageUrl;
    }

    public ArtistAddBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public GenreNameEnum getGenre() {
        return genre;
    }

    public ArtistAddBindingModel setGenre(GenreNameEnum genre) {
        this.genre = genre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
