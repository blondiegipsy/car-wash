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

    private final RelayHandler relayHandler;

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        relayHandler.startWasher(2, 10, 1);
        return "Relay turned on";
    }
}
