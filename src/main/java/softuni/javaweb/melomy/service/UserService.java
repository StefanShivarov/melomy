package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.service.UserServiceModel;
import softuni.javaweb.melomy.model.view.UserViewModel;

import java.util.List;

public interface UserService {

    UserServiceModel findById(Long id);

    void registerUser(UserServiceModel userServiceModel);

    void intializeUsers();

    boolean isAdmin(String username);

    List<UserViewModel> searchByUsernameContaining(String input);
}
