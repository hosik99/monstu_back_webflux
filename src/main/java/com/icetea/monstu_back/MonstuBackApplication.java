package com.icetea.monstu_back;

import com.icetea.monstu_back.r2dbc.sqlBuilder.AnnotationReader;
import com.icetea.monstu_back.r2dbc.sqlBuilder.KClassGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

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