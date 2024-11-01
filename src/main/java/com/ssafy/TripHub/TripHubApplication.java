package com.ssafy.TripHub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ssafy.TripHub.*.mapper")
public class TripHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripHubApplication.class, args);
	}

}
