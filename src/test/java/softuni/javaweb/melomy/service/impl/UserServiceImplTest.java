package softuni.javaweb.melomy.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.model.service.UserServiceModel;
import softuni.javaweb.melomy.model.view.UserViewModel;
import softuni.javaweb.melomy.repository.RoleRepository;
import softuni.javaweb.melomy.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserEntity testUser, testOrdinaryUser;
    private UserServiceModel userServiceModel;
    private UserViewModel ordinaryUserViewModel;
    private RoleEntity adminRole, userRole;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp(){

        userServiceImpl = new UserServiceImpl(mockUserRepository, mockRoleRepository, mockModelMapper, mockPasswordEncoder);

        adminRole = new RoleEntity();
        adminRole.setName(RoleNameEnum.ADMIN);

        userRole = new RoleEntity();
        userRole.setName(RoleNameEnum.USER);

        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setFullName("Test User");
        testUser.setPassword("testpass");
        testUser.setEmail("test@test.com");
        testUser.setRoles(Set.of(adminRole, userRole));

        userServiceModel = new UserServiceModel();
        userServiceModel.setId(1L);
        userServiceModel.setUsername("testUser");
        userServiceModel.setEmail("test@test.com");
        userServiceModel.setPassword("testpass");
        userServiceModel.setFullName("Test User");
        userServiceModel.setRoles(Set.of(adminRole, userRole));

        testOrdinaryUser = new UserEntity();
        testOrdinaryUser.setUsername("randomUser")
                .setEmail("random@random.com")
                .setFullName("Random Guy")
                .setPassword("randompass")
                .setRoles(Set.of(userRole));

        ordinaryUserViewModel = new UserViewModel()
                .setUsername("randomUser")
                .setEmail("random@random.com")
                .setRoles(Set.of("USER"));
    }

    @Test
    void testFindByIdShouldReturnCorrectUserServiceModel(){

        Mockito.when(mockUserRepository.findById(1L))
                .thenReturn(Optional.of(testUser));

        Mockito.when(mockModelMapper.map(testUser, UserServiceModel.class))
                .thenReturn(userServiceModel);

        var actual = userServiceImpl.findById(1L);


        Assertions.assertEquals(actual.getUsername(), userServiceModel.getUsername(),
                "Find By Id method doesn't work properly! UserServiceModel doesn't match!");

    }

    @Test
    void testIsAdminMethod(){

        Mockito.when(mockUserRepository.findByUsername("randomUser"))
                .thenReturn(Optional.of(testOrdinaryUser));

//        Assertions.assertTrue(userServiceImpl.isAdmin("testUser"));
        Assertions.assertFalse(userServiceImpl.isAdmin("randomUser"));
    }

}
