package softuni.javaweb.melomy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.model.service.UserServiceModel;
import softuni.javaweb.melomy.model.view.UserViewModel;
import softuni.javaweb.melomy.repository.RoleRepository;
import softuni.javaweb.melomy.repository.UserRepository;
import softuni.javaweb.melomy.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel findById(Long id) {
        return userRepository.
                findById(id).
                map(userEntity -> modelMapper.map(userEntity, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER)))
                .setFavouriteArtists(new ArrayList<>())
                .setFavouriteSongs(new ArrayList<>());

        userRepository.save(userEntity);
    }

    @Override
    public void intializeUsers() {

        if(userRepository.count() == 0){

            UserEntity testAdmin = new UserEntity()
                    .setFullName("Admin Adminov")
                    .setUsername("melomy_admin")
                    .setEmail("admin@adminov.com")
                    .setPassword(passwordEncoder.encode("adminpass"))
                    .setImageUrl(null)
                    .setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER), roleRepository.findByName(RoleNameEnum.ADMIN)));

            UserEntity genericUser = new UserEntity()
                    .setFullName("User Userski")
                    .setUsername("melomy_user")
                    .setEmail("user@user.com")
                    .setPassword(passwordEncoder.encode("userpass"))
                    .setImageUrl(null)
                    .setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER)));

            userRepository.save(testAdmin);
            userRepository.save(genericUser);
        }

    }

    @Override
    public boolean isAdmin(String username) {
        return userRepository
                .findByUsername(username)
                .get()
                .getRoles()
                .stream()
                .map(RoleEntity::getName)
                .anyMatch(roleNameEnum -> roleNameEnum == RoleNameEnum.ADMIN);
    }

    @Override
    public List<UserViewModel> searchByUsernameContaining(String input) {
        return userRepository
                .findAllByUsernameContaining(input)
                .stream()
                .map(this::mapToViewModel)
                .collect(Collectors.toList());
    }

    private UserViewModel mapToViewModel(UserEntity userEntity){

        return modelMapper.map(userEntity, UserViewModel.class)
                .setRoles(userEntity
                        .getRoles()
                        .stream()
                        .map(roleEntity -> roleEntity.getName().name())
                        .collect(Collectors.toSet()));
    }

}
