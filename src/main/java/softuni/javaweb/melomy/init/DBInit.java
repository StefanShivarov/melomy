package softuni.javaweb.melomy.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.javaweb.melomy.service.*;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final GenreService genreService;
    private final RoleService roleService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;


    public DBInit(UserService userService, GenreService genreService, RoleService roleService, AlbumService albumService, ArtistService artistService, SongService songService) {
        this.userService = userService;
        this.genreService = genreService;
        this.roleService = roleService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {


        roleService.initializeRoles();

        userService.intializeUsers();

        genreService.initializeGenres();


    }
}
