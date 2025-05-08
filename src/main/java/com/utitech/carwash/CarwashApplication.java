package com.utitech.carwash;

import com.pi4j.Pi4J;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarwashApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarwashApplication.class, args);
    }
}