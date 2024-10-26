package com.utitech.carwash.raspi;

import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relay")
public class RelayController {

    @Autowired
    private RelayHandler relayService;

    private static PiGpioDigitalOutput digitalOutput;

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        relayService.relaySwitch();
        return "Relay turned on";
    }
}
