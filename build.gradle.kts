import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("application")
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
	implementation("org.mongodb:bson")

	// Spring Security 및 WebFlux 의존성
//	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	//Swagger  http://localhost:8080/webjars/swagger-ui/index.html
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0")

	//anotation
	implementation("org.reflections:reflections:0.10.2")

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

// application 플러그인 설정 (mainClass 설정)  ./gradlew build
application {
	mainClass.set("com.icetea.monstu_back.r2dbc.sqlBuilder.KClassGenerator")
}

// 기존 생성된 파일을 삭제하는 태스크 추가
tasks.register<Delete>("cleanGeneratedClasses") {
	delete(fileTree("src/main/java/com/kclass/generated"))
}

// `generateKClasses` task 정의  ./gradlew generateKClasses. 생성된 클래스가 build 프로세스에 포함되어 컴파일이 진행
tasks.register<JavaExec>("generateKClasses") {
	classpath = sourceSets.main.get().runtimeClasspath
	mainClass.set("com.icetea.monstu_back.r2dbc.sqlBuilder.KClassGenerator")

	doLast {
		// 새로 생성된 클래스들을 sourceSets의 srcDirs에 추가
		val generatedDir = File("src/main/java/com/kclass/generated")
		if (generatedDir.exists()) {
			sourceSets.main.get().java.srcDir(generatedDir)
		}
	}
}

tasks.named("build") {
	dependsOn("generateKClasses")  //generateKClasses가 먼저 실행되고, 그 후에 build 태스크가 진행
}
//tasks.named("compileJava") {
//	dependsOn("generateKClasses") // `compileJava` 이전에 실행되도록 설정
//}

java {
	sourceSets {
		main {
			java.srcDir("src/main/java/com/kclass/generated")
		}
	}
}

//./gradlew clean build
