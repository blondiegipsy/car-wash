package com.utitech.carwash;

import com.pi4j.Pi4J;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.platform.Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CarwashApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarwashApplication.class, args);

        var pi4j = Pi4J.newAutoContext();

        Platform platform = pi4j.platforms().getDefault();

        // get default digital output provide for this platform
        DigitalOutputProvider provider = platform.provider(IOType.DIGITAL_OUTPUT);

        // create I/O instance configuration using the config builder
        DigitalOutputConfig config = DigitalOutputConfig.newBuilder(pi4j).address(17).build();

        // use factory to create/register  I/O instance
        DigitalOutput output = provider.create(config);

        // setup a digital output listener to listen for any state changes on the digital output
        output.addListener((DigitalStateChangeListener) System.out::println);

        // lets toggle the digital output state a few times
        output.toggle()
                .toggle()
                .toggle();

        // another friendly method of setting output state
        output.high()
                .low();

        // blink the output for 10 seconds
        output.blink(1, 10, TimeUnit.SECONDS);

        // shutdown Pi4J
        pi4j.shutdown();
    }
}