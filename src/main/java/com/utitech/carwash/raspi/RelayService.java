package com.utitech.carwash.raspi;// RelayService.java

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelayService {

    private Context pi4j;
    private DigitalOutput relayPin;


    @PostConstruct
    public void init() {
        // Configure GPIO 17 as a digital output pin
        DigitalOutputConfig config = DigitalOutput.newConfigBuilder(pi4j)
                .id("relayPin")
                .name("Relay Control Pin")
                .address(17) // GPIO 17 (BCM)
                .shutdown(DigitalState.LOW) // Set to LOW on shutdown
                .initial(DigitalState.HIGH) // Set to HIGH on startup
                .provider("pigpio-digital-output") // Use PiGPIO provider
                .build();

        // Create and initialize the relay pin
        this.relayPin = pi4j.create(config);

        // Set the relay to HIGH (ON)
        this.relayPin.high();

        // Ensure Pi4J context is closed when the app stops
        Runtime.getRuntime().addShutdownHook(new Thread(pi4j::shutdown));
    }

    // Optional: Method to toggle relay state
    public void toggleRelay() {
        this.relayPin.toggle();
    }
}
