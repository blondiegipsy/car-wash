package com.utitech.carwash.raspi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {

    private RelayHandler relayService;

    @GetMapping("/on")
    public String turnRelayOn() throws Exception {
        relayService.relaySwitch();
        return "Relay turned on";
    }
}
