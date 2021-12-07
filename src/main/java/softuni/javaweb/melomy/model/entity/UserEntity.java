package softuni.javaweb.melomy.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    private String username;
    private String fullName;
    private String email;
    private String password;
    private String imageUrl;
    private List<SongEntity> favouriteSongs;
    private List<ArtistEntity> favouriteArtists;
    private Set<RoleEntity> roles;


    public UserEntity(){

    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @ManyToMany
    public List<SongEntity> getFavouriteSongs() {
        return favouriteSongs;
    }

    public UserEntity setFavouriteSongs(List<SongEntity> favouriteSongs) {
        this.favouriteSongs = favouriteSongs;
        return this;
    }

    @ManyToMany
    public List<ArtistEntity> getFavouriteArtists() {
        return favouriteArtists;
    }

    public UserEntity setFavouriteArtists(List<ArtistEntity> favouriteArtists) {
        this.favouriteArtists = favouriteArtists;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
