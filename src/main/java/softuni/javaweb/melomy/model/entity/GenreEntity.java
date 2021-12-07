package softuni.javaweb.melomy.model.entity;

import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
public class GenreEntity extends BaseEntity{

    private GenreNameEnum name;
    private String description;
    private List<ArtistEntity> artists;

    public GenreEntity (){

    }

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    public GenreNameEnum getName() {
        return name;
    }

    public GenreEntity setName(GenreNameEnum name) {
        this.name = name;
        return this;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public GenreEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @ManyToMany
    public List<ArtistEntity> getArtists() {
        return artists;
    }

    public GenreEntity setArtists(List<ArtistEntity> artists) {
        this.artists = artists;
        return this;
    }
}
