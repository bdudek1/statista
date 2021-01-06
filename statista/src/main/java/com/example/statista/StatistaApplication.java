package com.example.statista;

import com.example.statista.entities.User;
import com.example.statista.services.UserService;
import org.apache.catalina.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatistaApplication {
	private static final Logger logger = LoggerFactory.getLogger(StatistaApplication.class);

	@Autowired
	//static UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(StatistaApplication.class, args);
		logger.info("Application started.");
//		userService.save(new User("user", "user@gmail.com", "user", "ROLE_USER"));
//		userService.save(new User("admin", "admin@gmail.com", "admin", "ROLE_ADMIN"));
	}

}
