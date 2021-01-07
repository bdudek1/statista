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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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

    public User findByUsername(String username){
        Optional<User> user = userRepo.findByUsername(username);
        return user.orElseThrow(() -> new RuntimeException("User " + username + " not found!"));
    }

    public User save(User user){
        logger.info("Saving user: " + user.toString());
        return userRepo.save(user);
    }

    @Transactional()
    public void updatePassword(String username, String newPassword){
        User user = findByUsername(username);
        user.setPassword(newPassword);
        userRepo.save(user);
        logger.info("Updating password of user: " + user.toString());
    }

    public void delete(User user){
        logger.info("Deleting user: " + user.toString());
        userRepo.delete(user);
    }

    public void deleteAll(){
        logger.info("Deleting all users");
        userRepo.deleteAll();
    }

    public long count(){
        logger.info("Counting users");
        return userRepo.count();
    }

    @PostConstruct
    void initUsers(){
//        User user = new User("user", "user@gmail.com", "user", "ROLE_USER");
//        User admin = new User("admin", "admin@gmail.com", "admin", "ROLE_ADMIN");
//        save(user);
//        save(admin);
//        delete(user);
//
//        logger.info("Loading user: " + userRepo.findByUsername("admin"));
    }
}
