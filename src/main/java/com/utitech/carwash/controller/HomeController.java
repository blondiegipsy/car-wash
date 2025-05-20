package com.utitech.carwash.controller;

import com.utitech.carwash.controller.request.WasherData;
import com.utitech.carwash.model.Tariffs;
import com.utitech.carwash.model.TariffsRepository;
import com.utitech.carwash.model.UserRepository;
import com.utitech.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final UserRepository userRepository;
    private final TariffsRepository tariffsRepository;
    private final UserService userService;

    @GetMapping("/user-data")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(userService.getBalance(username));
    }

    @GetMapping("/washing-status")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> washingData() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService);
    }

    @GetMapping("/tariffs")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getAllTariffs() {
        Tariffs tariffs = tariffsRepository.findAll().getFirst();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "washerTariff", tariffs.getWashingTime(),
                        "vacuumTariff", tariffs.getVacuumTime(),
                        "chassisWasherTariff", tariffs.getChassisWashingTime()
                ));
    }
}