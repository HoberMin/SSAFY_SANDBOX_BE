package com.example.skilltestingbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkillTestingBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillTestingBeApplication.class, args);
	}

}
