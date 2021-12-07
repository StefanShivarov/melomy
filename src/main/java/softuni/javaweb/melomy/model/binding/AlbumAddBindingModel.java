package softuni.javaweb.melomy.model.binding;

import javax.validation.constraints.NotBlank;

public class AlbumAddBindingModel {

    private String name;
    private String imageUrl;
    private String artist;

    public AlbumAddBindingModel() {
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public AlbumAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotBlank
    public String getImageUrl() {
        return imageUrl;
    }

    public AlbumAddBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public AlbumAddBindingModel setArtist(String artist) {
        this.artist = artist;
        return this;
    }
}
