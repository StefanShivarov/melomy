package softuni.javaweb.melomy.model.view;

public class AlbumViewModel {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String artistName;
    private Integer year;
    private Long artistId;

    public Long getId() {
        return id;
    }

    public AlbumViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AlbumViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AlbumViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AlbumViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public AlbumViewModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getArtistName() {
        return artistName;
    }

    public AlbumViewModel setArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public AlbumViewModel setArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }
}
