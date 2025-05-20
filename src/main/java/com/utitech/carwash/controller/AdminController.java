package com.utitech.carwash.controller;

import com.utitech.carwash.controller.request.BalanceRequest;
import com.utitech.carwash.controller.request.RegisterRequest;
import com.utitech.carwash.controller.request.TariffRequest;
import com.utitech.carwash.hardware.RelayService;
import com.utitech.carwash.model.Log;
import com.utitech.carwash.model.LogRepository;
import com.utitech.carwash.service.AdminService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final RelayService relayService;
    private final LogRepository logRepository;

    @PostMapping("/add-user")
    public ResponseEntity<?> newUser(@RequestBody RegisterRequest request) {
        try {
            adminService.newUser(request.username(), request.password());
            return ResponseEntity.ok("Felhasználó sikeresen létrehozva");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
    }

    @GetMapping("/delete-user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            adminService.deleteUser(username);
            return ResponseEntity.ok("Felhasználó sikeresen törölve");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
    }

    @PostMapping("/add-balance")
    public ResponseEntity<?> addBalance(@RequestBody BalanceRequest request) {
        try {
            adminService.addBalance(request.username(), request.balance());
            return ResponseEntity.ok("Egyenleg sikeresen feltöltve");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
    }

    @PostMapping("/change-tariffs")
    public ResponseEntity<?> setTariff(@RequestBody TariffRequest request) {
        try {
            adminService.setTariff(request.fee(), request.tariff());
            return ResponseEntity.ok("Sikeres művelet");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<Page<Log>> getLogs(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "15") int size) {
        Page<Log> logPage = logRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(logPage);
    }

    @PostMapping("/abort")
    public ResponseEntity<?> abortWashing(@RequestBody Integer washerNumber) {
        relayService.adminOverride(washerNumber);
        return ResponseEntity.ok("Mosás sikeresen elinditva/leállítva");
    }

    @PostMapping("/lamp")
    public ResponseEntity<?> toggleLamp(@RequestBody Integer lamp) {
        relayService.toggleLamp(lamp);
        return ResponseEntity.ok("Lámpa sikeresen felkapcsolva/leállítva");
    }
}