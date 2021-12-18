package softuni.javaweb.melomy.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class MelomyUserDetailsServiceTest {

    private UserEntity testUser;
    private RoleEntity adminRole, userRole;

    private MelomyUserDetailsService melomyUserDetailsService;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        melomyUserDetailsService = new MelomyUserDetailsService(mockUserRepository);

        adminRole = new RoleEntity();
        adminRole.setName(RoleNameEnum.ADMIN);

        userRole = new RoleEntity();
        userRole.setName(RoleNameEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("melomy_admin");
        testUser.setFullName("Admin Adminov");
        testUser.setEmail("admin@adminov.com");
        testUser.setPassword("adminpass");
        testUser.setRoles(Set.of(userRole, adminRole));
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                ()->melomyUserDetailsService.loadUserByUsername("someinvalidusername")
        );
    }

    @Test
    void testUserFound(){

        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        var actual = melomyUserDetailsService.loadUserByUsername(testUser.getUsername());

        String expectedRoles = "ROLE_ADMIN, ROLE_USER";
        String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        Assertions.assertEquals(actual.getUsername(), testUser.getUsername(), "Username doesn't match!");
        Assertions.assertEquals(actualRoles, expectedRoles, "Roles don't match!");
    }


}
