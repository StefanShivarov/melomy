package softuni.javaweb.melomy.service.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.javaweb.melomy.model.entity.AlbumEntity;
import softuni.javaweb.melomy.model.entity.ArtistEntity;
import softuni.javaweb.melomy.model.entity.GenreEntity;
import softuni.javaweb.melomy.model.entity.SongEntity;
import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;
import softuni.javaweb.melomy.repository.*;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test_admin", roles = {"ADMIN", "USER"})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    @AfterEach
    void tearDown(){
        songRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    void testRenderAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    void testRenderStatisticsPage() throws Exception{
        mockMvc.perform(get("/admin/statistics"))
                .andExpect(status().isOk())
                .andExpect(view().name("stats"));
    }

    @Test
    void testRenderAddSongPage() throws Exception{
        mockMvc.perform(get("/admin/songs/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-song"));
    }

    @Test
    void testRenderAddAlbumPage() throws Exception{
        mockMvc.perform(get("/admin/albums/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-album"));
    }

    @Test
    void testRenderAddArtistPage() throws Exception{
        mockMvc.perform(get("/admin/artists/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-artist"));
    }

    @Test
    void testRenderManageUsersPage() throws Exception{
        mockMvc.perform(get("/admin/users/manage"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-users"));
    }

    @Test
    void testAddSongSuccessfully() throws Exception{

        ArtistEntity artistEntity = new ArtistEntity()
                .setDescription("desc")
                .setName("testArtist")
                .setImageUrl("randomArtistUrl")
                .setGenre(genreRepository.findByName(GenreNameEnum.POP));

        artistEntity = artistRepository.save(artistEntity);

        AlbumEntity albumEntity = new AlbumEntity()
                .setName("testAlbum").setDescription("desc")
                .setImageUrl("randomImageUrl")
                .setYear(2020)
                .setArtist(artistEntity);

        albumEntity = albumRepository.save(albumEntity);

        mockMvc.perform(post("/admin/songs/add")
                .param("name", "Test Song Name")
                .param("songUrl", "random Url")
                .param("albumId", albumEntity.getId().toString())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, songRepository.count());
        Optional<SongEntity> justCreatedSong = songRepository.findByNameAndAlbum_Name("Test Song Name",
                "testAlbum");
        Assertions.assertTrue(justCreatedSong.isPresent());
        SongEntity newSong = justCreatedSong.get();

        Assertions.assertEquals("Test Song Name", newSong.getName());
        Assertions.assertEquals(albumEntity.getName(), newSong.getAlbum().getName());
        Assertions.assertEquals(artistEntity.getName(), newSong.getArtist().getName());
    }

    @Test
    void testAddSongError() throws Exception {
        mockMvc.perform(post("/admin/songs/add")
                .param("name", "test song add")
                .param("songUrl", "randomurl")
                .param("albumId", "definitelynotanid")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("songAddBindingModel",
                        "org.springframework.validation.BindingResult.songAddBindingModel"));

        Assertions.assertEquals(0, songRepository.count());
    }

    @Test
    void testAddAlbumSuccessfully() throws Exception{

        ArtistEntity artistEntity = new ArtistEntity()
                .setDescription("desc")
                .setName("testArtist")
                .setImageUrl("randomArtistUrl")
                .setGenre(genreRepository.findByName(GenreNameEnum.POP));

        artistEntity = artistRepository.save(artistEntity);

        mockMvc.perform(post("/admin/albums/add")
                        .param("name", "Test Album Name")
                        .param("description", "desc")
                        .param("year", "2020")
                        .param("imageUrl", "someImageUrl...")
                        .param("artistId", artistEntity.getId().toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, albumRepository.count());
        Optional<AlbumEntity> justCreatedAlbum = albumRepository.findByNameAndArtist_Id("Test Album Name",
                artistEntity.getId());
        Assertions.assertTrue(justCreatedAlbum.isPresent());
        AlbumEntity newAlbum = justCreatedAlbum.get();

        Assertions.assertEquals("Test Album Name", newAlbum.getName());
        Assertions.assertEquals(2020, newAlbum.getYear());
        Assertions.assertEquals(artistEntity.getName(), newAlbum.getArtist().getName());
    }

    @Test
    void testAddAlbumError() throws Exception {
        mockMvc.perform(post("/admin/albums/add")
                        .param("name", "Test Album Name")
                        .param("description", "desc")
                        .param("year", "2020")
                        .param("imageUrl", "someImageUrl...")
                        .param("artistId", "definitelynotanid")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("albumAddBindingModel",
                        "org.springframework.validation.BindingResult.albumAddBindingModel"));

        Assertions.assertEquals(0, albumRepository.count());
    }

    @Test
    void testAddArtistSuccessfully() throws Exception{

        GenreEntity genreEntity = genreRepository
                .findByName(GenreNameEnum.POP);

        mockMvc.perform(post("/admin/artists/add")
                        .param("name", "Test Artist Name")
                        .param("description", "desc")
                        .param("imageUrl", "someImageUrl...")
                        .param("genre", genreEntity.getName().name())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, artistRepository.count());
        Optional<ArtistEntity> justCreatedAlbum = artistRepository.findByName("Test Artist Name");
        Assertions.assertTrue(justCreatedAlbum.isPresent());
        ArtistEntity newArtist = justCreatedAlbum.get();

        Assertions.assertEquals("Test Artist Name", newArtist.getName());
        Assertions.assertEquals(genreEntity.getName().name(), newArtist.getGenre().getName().name());
    }

    @Test
    void testAddArtistError() throws Exception {
        mockMvc.perform(post("/admin/artists/add")
                        .param("name", "Test Artist Name")
                        .param("description", "desc")
                        .param("imageUrl", "someImageUrl...")
                        .param("genre", "definitelynotagenre")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("artistAddBindingModel",
                        "org.springframework.validation.BindingResult.artistAddBindingModel"));

        Assertions.assertEquals(0, artistRepository.count());
    }
}
