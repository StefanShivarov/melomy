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
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    @AfterEach
    void tearDown(){
        artistRepository.deleteAll();
    }

    @Test
    void testRenderSearchArtists() throws Exception {
        mockMvc.perform(get("/artists/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-artists"));
    }

    @Test
    void testIfSearchArtistsWorksProperly() throws Exception {
        mockMvc.perform(post("/artists/search")
                        .param("input", "")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/artists/search"));
    }

    @Test
    void testRenderSongDetails() throws Exception {

        ArtistEntity artistEntity = new ArtistEntity()
                .setDescription("desc")
                .setName("testArtist")
                .setImageUrl("randomArtistUrl")
                .setGenre(genreRepository.findByName(GenreNameEnum.POP));

        artistEntity = artistRepository.save(artistEntity);

        mockMvc.perform(get("/artists/"+artistEntity.getId()+"/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("artistViewModel"))
                .andExpect(view().name("artist"));
    }

    @Test
    void testDeleteSong() throws Exception {

        ArtistEntity artistEntity = new ArtistEntity()
                .setDescription("desc")
                .setName("testArtist")
                .setImageUrl("randomArtistUrl")
                .setGenre(genreRepository.findByName(GenreNameEnum.POP));

        artistEntity = artistRepository.save(artistEntity);

        mockMvc.perform(delete("/artists/"+artistEntity.getId()+"/delete")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/artists/search"));

        Assertions.assertEquals(0, artistRepository.count());
    }
}
