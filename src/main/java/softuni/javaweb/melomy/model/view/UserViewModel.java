package softuni.javaweb.melomy.model.view;

import java.util.Set;

public class UserViewModel {

    private Long id;
    private String username;
    private String email;
    private Set<String> roles;

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UserViewModel setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }
}
