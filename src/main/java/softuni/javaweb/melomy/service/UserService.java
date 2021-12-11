package softuni.javaweb.melomy.service;

import softuni.javaweb.melomy.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel findById(Long id);

    void registerUser(UserServiceModel userServiceModel);

    void intializeUsers();

    boolean isAdmin(String username);
}
