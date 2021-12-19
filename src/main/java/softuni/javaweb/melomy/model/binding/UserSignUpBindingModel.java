package softuni.javaweb.melomy.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserSignUpBindingModel {

    private String fullName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserSignUpBindingModel(){

    }

    public UserSignUpBindingModel(String username, String fullName,
                                  String email, String password,
                                  String confirmPassword) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Size(min = 3)
    public String getUsername() {
        return username;
    }

    public UserSignUpBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Size(min = 3, message = "This is a custom validation message...")
    public String getFullName() {
        return fullName;
    }

    public UserSignUpBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Email
    @Size(min = 6)
    public String getEmail() {
        return email;
    }

    public UserSignUpBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 3)
    public String getPassword() {
        return password;
    }

    public UserSignUpBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Size(min = 3)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserSignUpBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

}
