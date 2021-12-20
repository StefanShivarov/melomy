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
import softuni.javaweb.melomy.service.CommentService;
import softuni.javaweb.melomy.service.UserService;
import softuni.javaweb.melomy.web.exceptions.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CommentService commentService, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.commentService = commentService;
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
                .setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER)));

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
                    .setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER), roleRepository.findByName(RoleNameEnum.ADMIN)));

            UserEntity genericUser = new UserEntity()
                    .setFullName("User Userski")
                    .setUsername("melomy_user")
                    .setEmail("user@user.com")
                    .setPassword(passwordEncoder.encode("userpass"))
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

    @Override
    public void deleteUser(Long userId) {
        commentService.deleteAllCommentsByAuthor(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public void makeAdmin(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty()){
            throw new ObjectNotFoundException("User with id "+id+" not found!");
        }

        UserEntity user = userOpt.get();
        user.setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER), roleRepository.findByName(RoleNameEnum.ADMIN)));
        userRepository.save(user);
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
