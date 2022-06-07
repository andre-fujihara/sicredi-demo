package com.example.sicredidemo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.example.sicredidemo")
@EnableMongoRepositories(value = "com.example.sicredidemo.data")
public class SicrediDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicrediDemoApplication.class, args);
	}

}
