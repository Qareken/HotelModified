plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("javax.validation:validation-api:2.0.1.Final")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
	implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation ("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("redis.clients:jedis")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.testcontainers:postgresql")
	testImplementation ("org.springframework.security:spring-security-test")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
