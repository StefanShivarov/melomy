package softuni.javaweb.melomy.model.view;


public class SongViewModel {

    private Long id;
    private String name;
    private String songUrl;
    private Long artistId;
    private String artistName;
    private Long albumId;
    private String albumName;

    public Long getId() {
        return id;
    }

    public SongViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SongViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public SongViewModel setSongUrl(String songUrl) {
        this.songUrl = songUrl;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public SongViewModel setArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }

    public String getArtistName() {
        return artistName;
    }

    public SongViewModel setArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public SongViewModel setAlbumId(Long albumId) {
        this.albumId = albumId;
        return this;
    }

    public String getAlbumName() {
        return albumName;
    }

    public SongViewModel setAlbumName(String albumName) {
        this.albumName = albumName;
        return this;
    }
}
