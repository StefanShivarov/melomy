package softuni.javaweb.melomy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SongAddBindingModel {

    private String name;
    private String songUrl;
    private String album;

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

    public String getAlbum() {
        return album;
    }

    public SongAddBindingModel setAlbum(String album) {
        this.album = album;
        return this;
    }
}
