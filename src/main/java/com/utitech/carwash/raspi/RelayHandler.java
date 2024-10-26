package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.platform.Platform;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RelayHandler {

    public void switchRelay() {
        var pi4j = Pi4J.newAutoContext();

        // get default runtime platform
        Platform platform = pi4j.platforms().getDefault();

        // get default digital output provide for this platform
        DigitalOutputProvider provider = platform.provider(IOType.DIGITAL_OUTPUT);

        // create I/O instance configuration using the config builder
        DigitalOutputConfig config = DigitalOutputConfig.newBuilder(pi4j).address(3).build();

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
