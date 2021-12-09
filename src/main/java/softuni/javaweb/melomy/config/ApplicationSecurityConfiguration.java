package softuni.javaweb.melomy.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                authorizeRequests().
                // with this line we allow access to all static resources
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().

                        antMatchers("/", "/api/**").permitAll().
                // the next line allows access to the home page, login page and registration for everyone
                        antMatchers( "/users/login", "/users/sign-up", "/users/login-error").anonymous().
                // next we forbid all other pages for unauthenticated users.
                        antMatchers("/songs/**", "/albums/**", "/artists/**", "/explore").authenticated().

                        antMatchers("/songs/add", "/songs/delete/**",
                                "/albums/add", "albums/delete/**", "/artists/add,",
                                        "/artists/delete**", "/admin/**").hasRole(RoleNameEnum.ADMIN.name()).
                and().
                // configure login with login HTML form with two input fileds
                        formLogin().
                // our login page is located at http://<serveraddress>>:<port>/users/login
                        loginPage("/users/login").
                        loginProcessingUrl("/users/login").
                // this is the name of the <input..> in the login form where the user enters her email/username/etc
                // the value of this input will be presented to our User details service
                // those that want to name the input field differently, e.g. email may change the value below
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                // the name of the <input...> HTML filed that keeps the password
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).

                        failureUrl("/users/login-error").
                // The place where we should land in case that the login is successful
                        defaultSuccessUrl("/").
                // the place where I should land if the login is NOT successful

                and().
                logout().
                // This is the URL which spring will implement for me and will log the user out.
                        logoutUrl("/users/logout").
                // where to go after the logout.
                        logoutSuccessUrl("/").
                // remove the session from server
                        invalidateHttpSession(true).
                //delete the cookie that references my session
                        deleteCookies("JSESSIONID");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // This gives spring two important components.
        // 1. Our user details service that translates usernames/emails, phone numbers, etc/
        //    to UserDetails
        // 2. Password encoder - the component that can decide if the user password matches
        auth.
                userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder);


        // registration:
        // topsecretpass -> password encoder -> kfskjhfkjshfkjdshfkjdsh (hashed pwd)

        // login:
        // (username, raw_password) ->
        // password_encoder.matches(raw_password, hashed_pwd)
    }
}
