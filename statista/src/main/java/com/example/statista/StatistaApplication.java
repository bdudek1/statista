package com.example.statista;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

//@Profile("dev")
@SpringBootApplication
public class StatistaApplication {
	private static final Logger logger = LoggerFactory.getLogger(StatistaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StatistaApplication.class, args);
		logger.info("Application started.");
	}

}
