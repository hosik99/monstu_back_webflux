package com.icetea.monstu_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonstuBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonstuBackApplication.class, args);
	}

}
