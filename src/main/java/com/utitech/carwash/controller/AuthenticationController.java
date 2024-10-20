package com.utitech.carwash.controller;

import com.utitech.carwash.User;
import com.utitech.carwash.UserDTO;
import com.utitech.carwash.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserDTO user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body("Authentication Successful");
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> register(@RequestBody UserDTO user) {
        if (userRepository.findByUsername(user.username()).isPresent()) {
            return ResponseEntity.ok("User already exists");
        }

        User newUser = new User();
        newUser.setBalance(0L);
        newUser.setBalance(0L);
        newUser.setUsername(user.username());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }
}