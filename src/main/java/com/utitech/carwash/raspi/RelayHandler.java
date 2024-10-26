package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.platform.Platforms;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RelayHandler {

    public void relaySwitch() {
        var pi4j = Pi4J.newAutoContext();

        Platforms platforms = pi4j.platforms();

        platforms.describe().print(System.out);
    }
}