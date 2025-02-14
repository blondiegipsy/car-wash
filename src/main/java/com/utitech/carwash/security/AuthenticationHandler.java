package com.utitech.carwash.security;

import com.utitech.carwash.controller.request.AuthenticationRequest;
import com.utitech.carwash.controller.request.AuthenticationResponse;
import com.utitech.carwash.model.User;
import com.utitech.carwash.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationHandler {

    private final AuthenticationManager authenticationManager;
    private final JWTService jWTService;
    private final UserRepository userRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password supplied");
        }

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        var jwt = jWTService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}
