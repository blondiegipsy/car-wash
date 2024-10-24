package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;

public class GPIOManager {

    private final DigitalOutput digitalOutput;

    public GPIOManager() {
        Context pi4j = Pi4J.newAutoContext();

        DigitalOutputConfigBuilder configBuilder = DigitalOutput.newConfigBuilder(pi4j)
                .id("gpio-output")
                .address(5)
                .shutdown(DigitalState.HIGH)
                .initial(DigitalState.HIGH);
        this.digitalOutput = pi4j.create(configBuilder);
    }

    public void activatePin(int count) {
        for (int i = 0; i < count; i++) {
            // Set the pin HIGH
            digitalOutput.high();
            sleep();  // Delay for 500ms (can be adjusted)

            // Set the pin LOW
            digitalOutput.low();
            sleep();  // Delay for 500ms (can be adjusted)
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}