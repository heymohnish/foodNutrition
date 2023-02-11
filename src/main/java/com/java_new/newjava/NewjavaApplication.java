package com.java_new.newjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
// import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
@SpringBootApplication
@EnableReactiveMongoRepositories
public class NewjavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewjavaApplication.class, args);
	}

}
