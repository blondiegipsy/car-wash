package com.utitech.carwash.raspi;

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

    @Autowired
    public RelayController(Context pi4j) {

        // Configure GPIO pin BCM 17 as digital output
        relay = pi4j.dout().create(17);
    }

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        relay.high();
        relay.low();
        return "Relay turned on";
    }
}
