package softuni.javaweb.melomy.model.service;


public class SongServiceModel {

    private Long id;
    private String name;
    private String songUrl;
    private Long artistId;
    private Long albumId;

    public SongServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public SongServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SongServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public SongServiceModel setSongUrl(String songUrl) {
        this.songUrl = songUrl;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public SongServiceModel setArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public SongServiceModel setAlbumId(Long albumId) {
        this.albumId = albumId;
        return this;
    }
}
