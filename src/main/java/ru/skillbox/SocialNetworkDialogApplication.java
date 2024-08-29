package ru.skillbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SocialNetworkDialogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkDialogApplication.class, args);

	}

}
