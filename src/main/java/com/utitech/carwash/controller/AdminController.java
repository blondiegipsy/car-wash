package com.utitech.carwash.controller;

import com.utitech.carwash.controller.request.BalanceRequest;
import com.utitech.carwash.controller.request.RegisterRequest;
import com.utitech.carwash.service.AdminService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add-user")
    public ResponseEntity<?> newUser(@ModelAttribute RegisterRequest request) {
        try {
            adminService.newUser(request.username(), request.password());
            return ResponseEntity.ok("Felhasználó sikeresen létrehozva");
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Felhasználó már létezik");
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        adminService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/add-balance")
    public ResponseEntity<?> addBalance(@ModelAttribute BalanceRequest request) {
        adminService.addBalance(request.username(), request.balance());
        return ResponseEntity.ok().build();
    }
}
