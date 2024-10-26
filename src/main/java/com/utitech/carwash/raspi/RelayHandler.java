package com.utitech.carwash.raspi;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RelayHandler {

    private final DigitalOutput relay;
    private final Context pi4jContext;


    public RelayHandler(DigitalOutput relay, Context pi4jContext) {
        this.relay = relay;
        this.pi4jContext = pi4jContext;
    }

    public void switchRelay() {
        try {
            // Setup a digital output listener for state changes
            relay.addListener(digitalStateChangeEvent ->
                    System.out.println("Relay state changed to: " + digitalStateChangeEvent.state()));

            // Toggle examples
            relay.toggle()
                    .toggle()
                    .toggle();

            // Direct state control
            relay.high()
                    .low();

            // Blink example
            relay.blink(1, 10, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException("Failed to control relay", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            relay.low(); // Ensure relay is off when shutting down
            pi4jContext.shutdown();
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}
