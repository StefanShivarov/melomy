package softuni.javaweb.melomy.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.javaweb.melomy.service.*;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final GenreService genreService;
    private final RoleService roleService;

    public DBInit(UserService userService, GenreService genreService, RoleService roleService) {
        this.userService = userService;
        this.genreService = genreService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {


        roleService.initializeRoles();

        userService.intializeUsers();

        genreService.initializeGenres();


    }
}
