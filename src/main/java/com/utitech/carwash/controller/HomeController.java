package com.utitech.carwash.controller;

import com.utitech.carwash.model.*;
import com.utitech.carwash.service.RelayHandler;
import com.utitech.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller()
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;
    private final TariffsRepository tariffsRepository;
    private final RelayHandler relayHandler;
    private final LogRepository logRepository;

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
    public String admin(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Log> logPage = logRepository.findAll(PageRequest.of(page, size));
        Tariffs tariffs = tariffsRepository.findAll().getFirst();

        model.addAttribute("states", relayHandler.getStates());
        model.addAttribute("users", getAllUsers());
        Long totalBalance = userRepository.findTotalBalance();
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("userCount", userRepository.getAllUsersNumber());
        model.addAttribute("logPage", logPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("washerTariff", tariffs.getSecondForWashing());
        model.addAttribute("vacuumTariff", tariffs.getSecondForVacuuming());
        return "admin";
    }

    @GetMapping("/admin/logs")
    @ResponseBody
    public ResponseEntity<Page<Log>> getLogs(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Page<Log> logPage = logRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(logPage);
    }

    private List<User> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> !user.getUsername().equals(currentUsername))
                .collect(Collectors.toList());
    }
}