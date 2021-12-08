package softuni.javaweb.melomy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AlbumAddBindingModel {

    private String name;
    private String imageUrl;
    private String description;
    private Integer year;
    private Long artistId;

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

    public String getDescription() {
        return description;
    }


    public AlbumAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @NotNull
    @Positive
    public Integer getYear() {
        return year;
    }

    public AlbumAddBindingModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    @NotNull
    public Long getArtistId() {
        return artistId;
    }

    public AlbumAddBindingModel setArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }
}
