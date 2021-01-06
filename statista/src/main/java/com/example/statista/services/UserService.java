package com.example.statista.services;

import com.example.statista.entities.User;
import com.example.statista.repositories.UserRepository;
import com.example.statista.util.StringToDoubleListConverter;
import com.example.statista.util.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
            return new UserPrincipal(user.orElseThrow(() -> new RuntimeException("User " + username + " not found!")));
    }

    public User save(User user){
        logger.info("Saving user: " + user.toString());
        return userRepo.save(user);
    }

    @PostConstruct
    void initUsers(){
        save(new User("user", "user@gmail.com", "user", "ROLE_USER"));
        save(new User("admin", "admin@gmail.com", "admin", "ROLE_ADMIN"));
    }
}
