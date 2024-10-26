package com.utitech.carwash.raspi;

import com.pi4j.library.pigpio.PiGpio_GPIO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {

    private PiGpio_GPIO service;

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        service.gpioWrite(17, 1);
        return "Relay turned on";
    }

    @GetMapping("/off")
    public String turnRelayOff() throws Exception {
        service.gpioWrite(17, 0);
        return "Relay turned off";
    }
}