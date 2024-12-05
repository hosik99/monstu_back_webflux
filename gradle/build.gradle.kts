plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.icetea"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//r2dbc
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	// PostgreSQL R2DBC 의존성
	implementation("org.postgresql:r2dbc-postgresql")  // R2DBC PostgreSQL
	// MongoDB Reactive 의존성
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive") // MongoDB Reactive

	// Spring Security 및 WebFlux 의존성
//	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Lombok 의존성
	compileOnly("org.projectlombok:lombok")

	// 개발 및 테스트 관련 의존성
	developmentOnly("org.springframework.boot:spring-boot-devtools")
//	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core")  // 버전은 필요에 맞게 설정
	testImplementation("io.projectreactor:reactor-test")
//	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
