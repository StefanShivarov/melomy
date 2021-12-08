package softuni.javaweb.melomy.model.view;

import java.util.List;

public class ArtistViewModel {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String genre;
    private List<AlbumViewModel> albums;

    public ArtistViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ArtistViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArtistViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArtistViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public ArtistViewModel setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public List<AlbumViewModel> getAlbums() {
        return albums;
    }

    public ArtistViewModel setAlbums(List<AlbumViewModel> albums) {
        this.albums = albums;
        return this;
    }
}
