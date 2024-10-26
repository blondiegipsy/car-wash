package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PiConfig {

    @Bean
    public Context pi4jContext() {
        // Initialize Pi4J with proper platform
        return Pi4J.newAutoContext();
    }

    @Bean
    public DigitalOutput switchRelay(Context pi4jContext) {

        DigitalOutputConfigBuilder digitalConfig = DigitalOutput.newConfigBuilder(pi4jContext)
                .id("relay")
                .name("Relay Switch")
                .address(17)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");

        return pi4jContext.create(digitalConfig);
    }
}
