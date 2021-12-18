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
import softuni.javaweb.melomy.model.entity.SongEntity;
import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;
import softuni.javaweb.melomy.repository.AlbumRepository;
import softuni.javaweb.melomy.repository.ArtistRepository;
import softuni.javaweb.melomy.repository.GenreRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test_admin", roles = {"ADMIN", "USER"})
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    @AfterEach
    void tearDown(){
        albumRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    void testRenderSearchAlbums() throws Exception {

        mockMvc.perform(get("/albums/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-albums"));
    }

    @Test
    void testIfSearchAlbumsWorksProperly() throws Exception {
        mockMvc.perform(post("/albums/search")
                .param("input", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/albums/search"));
    }

    @Test
    void testRenderSongDetails() throws Exception {

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

        mockMvc.perform(get("/albums/"+albumEntity.getId()+"/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("albumViewModel"))
                .andExpect(view().name("album"));
    }

    @Test
    void testDeleteSong() throws Exception {

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

        mockMvc.perform(delete("/albums/"+albumEntity.getId()+"/delete")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/albums/search"));

        Assertions.assertEquals(0, albumRepository.count());
    }
}