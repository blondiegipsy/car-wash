plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.utitech"
version = "1.0.0"

repositories {
    mavenCentral()
}

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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    annotationProcessor("org.projectlombok:lombok")
    implementation("com.pi4j:pi4j-core:2.7.0")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:2.7.0")
    implementation("com.pi4j:pi4j-plugin-pigpio:2.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    // Enable executable jar
    launchScript()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    // Include all dependencies
    archiveClassifier.set("all")

    // Ensure application.properties is included
    from("src/main/resources") {
        include("application.properties")
        into("BOOT-INF/classes")
    }
}