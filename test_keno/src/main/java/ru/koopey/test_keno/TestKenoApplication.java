package ru.koopey.test_keno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestKenoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestKenoApplication.class, args);
	}
}
