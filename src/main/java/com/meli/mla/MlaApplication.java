package com.meli.mla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.meli.mla.configuration.model"})
@EnableJpaRepositories(basePackages = "com.meli.mla.configuration.repository")
public class MlaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlaApplication.class, args);
	}

}
