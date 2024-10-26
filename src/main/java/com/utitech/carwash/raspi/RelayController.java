package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {

    private final DigitalOutput relay;
    Context context = Pi4J.newAutoContext();

    @Autowired
    public RelayController(Context context) {

        // Configure GPIO pin BCM 17 as digital output
        relay = context.dout().create(17);
    }

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        relay.high();
        context.describe();
        context.boardInfo();
        context.hasIO("17");
        context.getPlatform();
        context.providers();
        context.registry();
        System.out.println(relay);
        System.out.println(relay.describe());
        System.out.println(relay.provider());
        System.out.println(relay.getAddress());
        System.out.println(context.boardInfo());
        System.out.println(context.registry());
        System.out.println(context.providers());
        System.out.println(relay.state());
        context.shutdown();
        return "Relay turned on";
    }
}
