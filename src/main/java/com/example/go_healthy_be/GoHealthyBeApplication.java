package com.example.go_healthy_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoHealthyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoHealthyBeApplication.class, args);
	}

	
}
