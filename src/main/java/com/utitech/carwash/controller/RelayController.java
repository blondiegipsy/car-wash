package com.utitech.carwash.controller;

import com.utitech.carwash.service.RelayHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RelayController {

    private final RelayHandler relayHandler;

    @GetMapping("/washing")
    public String turnRelayOn(@RequestBody WashingRequest request) {

        relayHandler.mainWashing(request.washer(),"faszom", request.balance());
        return "Mosó elindult";
    }

    @GetMapping("/state")
    public Map<String, Boolean> getWasherState() {
        Map<String, Boolean> state = new HashMap<>();
        state.put("washer-1",relayHandler.isWasher1state());
        state.put("washer-2",relayHandler.isWasher2state());
        return state;
    }
}