package softuni.javaweb.melomy.service.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.repository.RoleRepository;
import softuni.javaweb.melomy.repository.UserRepository;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "georgi")
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void initUser(){
        userRepository.deleteAll();
//        RoleEntity roleAdmin = new RoleEntity().setName(RoleNameEnum.ADMIN);
//        RoleEntity roleUser = new RoleEntity().setName(RoleNameEnum.USER);
//        roleRepository.saveAll(List.of(roleAdmin, roleUser));
        RoleEntity roleUser = roleRepository.findByName(RoleNameEnum.USER);
        UserEntity user= new UserEntity()
                .setEmail("georgi@petrov.com")
                .setFullName("Georgi Petrov")
                .setPassword("123456")
                .setUsername("georgi")
                .setRoles(Set.of(roleUser));

    }

    @AfterEach
    void tearDownRecentUsers(){
        userRepository.deleteAll();
    }

    @Test
    public void getIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("explore"));
    }
//    @Test
//    public void getAbout() throws Exception {
//        mockMvc.perform(get("/about"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("about"));
//    }
}
