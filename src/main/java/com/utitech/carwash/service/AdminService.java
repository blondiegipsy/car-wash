package com.utitech.carwash.service;

import com.utitech.carwash.model.*;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TariffsRepository tariffsRepository;


    public void newUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new EntityExistsException("Felhasználó már létezik");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsAdmin(false);
        user.setBalance(0L);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new EntityExistsException("Felhasználó nem létezik");
        }
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public void addBalance(String username, Long amount) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" " + username));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }


    @Transactional
    public void setTariff(int washingFee, int tariffId) {
        if (washingFee < 0) {
            throw new IllegalArgumentException("Invalid washing fee");
        }

        Tariffs tariff = tariffsRepository.findAll().getFirst();
        switch (tariffId) {
            case 0:
                tariff.setWashingTime(washingFee);
                break;
            case 1:
                tariff.setVacuumTime(washingFee);
                break;
            case 2:
                tariff.setChassisWashingTime(washingFee);
                break;
            default:
                throw new IllegalArgumentException("Invalid tariff");
        }

        tariffsRepository.save(tariff);
    }
}