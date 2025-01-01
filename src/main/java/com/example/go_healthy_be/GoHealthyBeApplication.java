package com.example.go_healthy_be;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class GoHealthyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoHealthyBeApplication.class, args);
	}

	 @PostConstruct
    public void init() {
        // Set timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
