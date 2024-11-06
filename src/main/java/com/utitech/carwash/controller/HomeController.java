package com.utitech.carwash.controller;

import com.utitech.carwash.model.Tariffs;
import com.utitech.carwash.model.TariffsRepository;
import com.utitech.carwash.model.User;
import com.utitech.carwash.model.UserRepository;
import com.utitech.carwash.service.RelayHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller()
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;
    private final TariffsRepository tariffsRepository;
    private final RelayHandler relayHandler;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userBalance = userRepository.findByUsername(username).get().getBalance();
        Tariffs tariffs = tariffsRepository.findAll().getFirst();
        model.addAttribute("userBalance", userBalance);
        model.addAttribute("washingTariff", tariffs.getSecondForWashing());
        model.addAttribute("vacuumTariff", tariffs.getSecondForVacuuming());
        model.addAttribute("washer1", relayHandler.isWasher1state());
        model.addAttribute("washer2", relayHandler.isWasher2state());
        model.addAttribute("vacuum", relayHandler.isVacuumState());
        return "dashboard";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(Model model) {
        model.addAttribute("users", getAllUsers());
        Long totalBalance = userRepository.findTotalBalance();
        model.addAttribute("totalBalance", totalBalance);

        return "admin";
    }

    private List<User> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> !user.getUsername().equals(currentUsername))
                .collect(Collectors.toList());
    };
}