package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.platform.Platforms;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RelayHandler {

    public void relaySwitch() {
        var pi4j = Pi4J.newAutoContext();

        Platforms platforms = pi4j.platforms();

        pi4j.describe().size();

        platforms.describe().print(System.out);

        final int PIN_LED = 17; // PIN 15 = BCM 22

        var led = pi4j.digitalOutput().create(PIN_LED);

        while (true) {
            led.high();
        }
    }
}