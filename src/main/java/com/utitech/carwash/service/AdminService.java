package com.utitech.carwash.service;

import com.utitech.carwash.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void newUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new EntityExistsException("Felhasználó már létezik");
        }
        com.utitech.carwash.user.User user = new com.utitech.carwash.user.User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new EntityExistsException("Felhasználó nem létezik");
        }
        userRepository.delete(userRepository.findByUsername(username).get());
    }

    public void addBalance(String username, Long amount) {
        com.utitech.carwash.user.User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" " + username));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }
}
