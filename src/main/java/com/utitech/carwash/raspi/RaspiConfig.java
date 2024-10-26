package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaspiConfig {
    @Bean
    public Context pi4jContext() {
        return Pi4J.newAutoContext();
    }
}
