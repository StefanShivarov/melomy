package softuni.javaweb.melomy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SongAddBindingModel {

    private String name;
    private String songUrl;
    private Long albumId;

    public SongAddBindingModel() {
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public SongAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotBlank
    public String getSongUrl() {
        return songUrl;
    }

    public SongAddBindingModel setSongUrl(String songUrl) {
        this.songUrl = songUrl;
        return this;
    }

    @NotNull
    public Long getAlbumId() {
        return albumId;
    }

    public SongAddBindingModel setAlbumId(Long albumId) {
        this.albumId = albumId;
        return this;
    }
}
