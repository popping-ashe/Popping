package com.ashe.popping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoppingApplication.class, args);
	}

}
