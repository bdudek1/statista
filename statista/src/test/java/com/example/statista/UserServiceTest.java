package com.example.statista;

import com.example.statista.entities.User;
import com.example.statista.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @BeforeEach
    void clearUsersTable(){
        userService.deleteAll();
    }

    @Test
    void shouldAddUsers(){
        addSomeUsers();
        assertEquals(3, userService.count());
    }

    @Test
    void shouldFindUser(){
        userService.save(new User("user", "user@gmail.com", "user", "ROLE_USER"));
        assertNotNull(userService.findByUsername("user"));
        assertThrows(RuntimeException.class, () -> userService.findByUsername("aasddwd"));
    }

    @Test
    void shouldDeleteUser(){
        addSomeUsers();
        assertEquals(3, userService.count());
        userService.delete(userService.findByUsername("john"));
        assertEquals(2, userService.count());
        assertThrows(RuntimeException.class, () -> userService.findByUsername("john"));
    }

    @Test
    void shouldChangePassword(){
        addSomeUsers();
        assertEquals(userService.findByUsername("john").getPassword(), "john");
        userService.updatePassword("john", "john2");
        assertEquals(userService.findByUsername("john").getPassword(), "john2");
        assertNotEquals(userService.findByUsername("john").getPassword(), "john");
    }

    void addSomeUsers(){
        userService.save(new User("user", "user@gmail.com", "user", "ROLE_USER"));
        userService.save(new User("admin", "admin@gmail.com", "admin", "ROLE_ADMIN"));
        userService.save(new User("john", "john@gmail.com", "john", "ROLE_USER"));
    }

}
