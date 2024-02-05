package com.test.xraydemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Xraydemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Xraydemo1Application.class, args);
	}  
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}


}
