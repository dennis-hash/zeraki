package com.example.zeraki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZerakiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZerakiApplication.class, args);
	}
}
