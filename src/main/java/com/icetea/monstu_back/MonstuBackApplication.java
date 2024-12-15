package com.icetea.monstu_back;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
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
//LocalDateTime localDateTime = LocalDateTime.now();
//Instant instant = localDateTime.toInstant(ZoneOffset.UTC); // UTC로 변환
//
//// Instant를 Date로 변환 (MongoDB에 저장할 때 Date 타입을 사용)
//Date date = Date.from(instant);


//ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime()