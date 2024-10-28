package com.utitech.carwash.controller;

import com.utitech.carwash.controller.request.WashingRequest;
import com.utitech.carwash.service.RelayHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RelayController {

    private final RelayHandler relayHandler;

    @PostMapping("/washing")
    public ResponseEntity<?> turnRelayOn(@RequestBody WashingRequest request) {
     /*   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();*/
        try {
            relayHandler.mainWashing(request.washer(), "faszom", request.balance());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/state")
    public String getWasherState(Model model) {
        Map<String, Boolean> state = new HashMap<>();
        state.put("washer-1",relayHandler.isWasher1state());
        state.put("washer-2",relayHandler.isWasher2state());
        model.addAttribute("state", state);
        return "dashboard";
    }

}