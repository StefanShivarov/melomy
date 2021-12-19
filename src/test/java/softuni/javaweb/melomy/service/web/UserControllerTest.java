package softuni.javaweb.melomy.service.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.repository.RoleRepository;
import softuni.javaweb.melomy.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String TEST_SIGNUP_USER_EMAIL = "gosho@gosho.com";
    private static final String TEST_SIGNUP_USER_USERNAME = "gosheto";
    private static final String TEST_SIGNUP_USER_PASSWORD = "goshetopass";
    private static final String TEST_SIGNUP_USER_FULL_NAME = "Georgi Georgiev";


    @BeforeEach
    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    void testRenderRegisterForm() throws Exception {
        mockMvc.perform(get("/users/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up"));
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/users/sign-up").
                param("fullName", TEST_SIGNUP_USER_FULL_NAME).
                param("username", TEST_SIGNUP_USER_USERNAME).
                param("email", TEST_SIGNUP_USER_EMAIL).
                param("password", TEST_SIGNUP_USER_PASSWORD).
                param("confirmPassword", TEST_SIGNUP_USER_PASSWORD).
                with(csrf()).
                contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());

        Optional<UserEntity> justCreatedUser = userRepository.findByUsername(TEST_SIGNUP_USER_USERNAME);

        Assertions.assertTrue(justCreatedUser.isPresent());

        UserEntity newUser = justCreatedUser.get();

        Assertions.assertEquals(TEST_SIGNUP_USER_USERNAME, newUser.getUsername());
        Assertions.assertEquals(TEST_SIGNUP_USER_FULL_NAME, newUser.getFullName());
        Assertions.assertEquals(TEST_SIGNUP_USER_EMAIL, newUser.getEmail());
    }

    @Test
    void testRegisterShouldRedirectToRegisterIfPasswordsNotMatch() throws Exception {
        mockMvc.perform(post("/users/sign-up").
                        param("username", TEST_SIGNUP_USER_USERNAME).
                        param("fullName", TEST_SIGNUP_USER_FULL_NAME).
                        param("email", TEST_SIGNUP_USER_EMAIL).
                        param("password", TEST_SIGNUP_USER_PASSWORD).
                        param("confirmPassword", "test123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/sign-up"))
                .andExpect(flash().attribute("badCredentials", true));
    }

    @Test
    void testRenderLoginForm() throws Exception {
        mockMvc.perform(get( "/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginErrorRedirectToLoginAgain() throws Exception {

        UserEntity testLoginUser = new UserEntity()
                .setFullName("Test Login User")
                .setEmail("test@loginuser.com")
                .setUsername("testLoginUser")
                .setPassword(passwordEncoder.encode("testLoginPass"))
                .setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER)));

        userRepository.save(testLoginUser);

        mockMvc.perform(post("/users/login-error")
                        .param("username", "123")
                        .param("password", "set")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    void testLoginUser() throws Exception{
        mockMvc.perform(post("/users/login")
                .param("username", "testLoginUser")
                .param("password", "testLoginPass")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

}
