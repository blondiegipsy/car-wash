package com.utitech.carwash.controller;

import com.utitech.carwash.controller.request.AuthenticationRequest;
import com.utitech.carwash.controller.request.AuthenticationResponse;
import com.utitech.carwash.security.AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Your frontend URL
public class AuthenticationController {

    private final AuthenticationHandler authenticationHandler;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok().body(authenticationHandler.authenticate(request));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok().body(new AuthenticationResponse("INVALID_CREDENTIALS"));
        }
    }
}