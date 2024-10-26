package com.utitech.carwash.raspi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relay")
public class RelayController {

    @Autowired
    private RelayHandler relayService;

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        relayService.switchRelay();
        return "Relay turned on";
    }
}
