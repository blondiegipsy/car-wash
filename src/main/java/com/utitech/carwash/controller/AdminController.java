package com.utitech.carwash.controller;

import com.utitech.carwash.controller.request.BalanceRequest;
import com.utitech.carwash.controller.request.RegisterRequest;
import com.utitech.carwash.service.AdminService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

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

    @GetMapping("/washing-fee")
    public ResponseEntity<?> setWashingFee(@RequestParam("washerRate") int washerRate) {
        try {
            adminService.setWashingFee(washerRate);
            return ResponseEntity.ok("Sikeres művelet");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
    }

    @GetMapping("/vacuum-fee")
    public ResponseEntity<?> setVacuumingFee(@RequestParam("vacuumRate") int vacuumRate) {
        try {
            adminService.setVacuumingFee(vacuumRate);
            return ResponseEntity.ok("Sikeres művelet");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
    }
}