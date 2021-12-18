package softuni.javaweb.melomy.service.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;
import softuni.javaweb.melomy.model.binding.CommentAddBindingModel;
import softuni.javaweb.melomy.model.entity.CommentEntity;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.SongEntity;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.repository.CommentRepository;
import softuni.javaweb.melomy.repository.RoleRepository;
import softuni.javaweb.melomy.repository.SongRepository;
import softuni.javaweb.melomy.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser(username = "adminUser")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentRestControllerTest {

    private static final String COMMENT_1 = "I am tired of testing...";
    private static final String COMMENT_2 = "It's getting annoying!";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser;
    private RoleEntity adminRole, userRole;

    @BeforeEach
    void setUp(){

        adminRole = roleRepository.findByName(RoleNameEnum.ADMIN);
        userRole = roleRepository.findByName(RoleNameEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("adminUser");
        testUser.setEmail("admin@admin.com");
        testUser.setPassword("adminpass");
        testUser.setFullName("Admin User");
        testUser.setRoles(Set.of(adminRole, userRole));

        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown(){
        commentRepository.deleteAll();
        songRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception{

        var song = initComments(initSong());

        mockMvc.perform(get("/api/"+song.getId()+"/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].message", is(COMMENT_1))).
                andExpect(jsonPath("$.[1].message", is(COMMENT_2)));
    }

    @Test
    void testCreateComment() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel()
                .setMessage(COMMENT_1);

        var emptySong = initSong();

        mockMvc.perform(
                post("/api/"+emptySong.getId()+"/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/" + emptySong.getId() + "/comments/"+
                        commentRepository.findFirstBySong_Id(emptySong.getId()).get().getId())))
                .andExpect(jsonPath("$.message").value(is(COMMENT_1)));
    }

    @Test
    void testCreateCommentError() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel()
                .setMessage("");
        var emptySong = initSong();
        mockMvc.perform(
                        post("/api/"+emptySong.getId()+"/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentAddBindingModel))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    private SongEntity initSong(){
        SongEntity song = new SongEntity();
        song.setName("Test song")
                .setSongUrl("randomUrl");
        return songRepository.save(song);
    }

    private SongEntity initComments(SongEntity songEntity){

        CommentEntity comment1 = new CommentEntity();
        comment1.setCreated(LocalDateTime.now());
        comment1.setContent(COMMENT_1);
        comment1.setSong(songEntity);
        comment1.setAuthor(testUser);

        CommentEntity comment2 = new CommentEntity();
        comment2.setCreated(LocalDateTime.now());
        comment2.setContent(COMMENT_2);
        comment2.setSong(songEntity);
        comment2.setAuthor(testUser);

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        songEntity.setComments(List.of(comment1, comment2));
        return songRepository.save(songEntity);
    }
}
