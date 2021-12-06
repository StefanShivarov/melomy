package softuni.javaweb.melomy.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.UserEntity;
import softuni.javaweb.melomy.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MelomyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MelomyUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.
                findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User with username: "+ username +" was not found."));

        return map(userEntity);
    }

    private UserDetails map(UserEntity userEntity){

        Set<GrantedAuthority> grantedAuthorities = userEntity.
                getRoles().
                stream().
                map(r->new SimpleGrantedAuthority("ROLE_"+r.getName().name())).
                collect(Collectors.toUnmodifiableSet());

        return new User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities);
    }
}
