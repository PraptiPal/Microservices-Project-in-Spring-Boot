package com.micro.ratingservice;

import com.micro.ratingservice.repositories.RatingRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
public class RatingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

}
