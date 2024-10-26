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

        var output = pi4j.dout().create(17);
        output.config().shutdownState(DigitalState.LOW);


// setup a digital output listener to listen for any state changes on the digital output
        output.addListener(System.out::println);

// lets invoke some changes on the digital output
        output.state(DigitalState.HIGH)
                .state(DigitalState.LOW)
                .state(DigitalState.HIGH)
                .state(DigitalState.LOW);

// lets toggle the digital output state a few times
        output.toggle()
                .toggle()
                .toggle();

// another friendly method of setting output state
        output.high()
                .low();

// lets read the digital output state
        System.out.print("CURRENT DIGITAL OUTPUT [" + output + "] STATE IS [");
        System.out.println(output.state() + "]");

    }
}