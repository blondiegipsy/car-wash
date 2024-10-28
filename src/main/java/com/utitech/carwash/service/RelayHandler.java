package com.utitech.carwash.service;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.utitech.carwash.user.User;
import com.utitech.carwash.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class RelayHandler {
    private final Context pi4j = Pi4J.newAutoContext();
    private final TaskScheduler taskScheduler;
    private final UserRepository userRepository;

    private static final int BUTTON_DISABLE_1 = 23;
    private static final int BUTTON_DISABLE_2 = 24;
    private static final int WASHER_1 = 17;
    private static final int WASHER_2 = 27;

    private final DigitalOutput buttonDisable1 = pi4j.digitalOutput().create(BUTTON_DISABLE_1);
    private final DigitalOutput buttonDisable2 = pi4j.digitalOutput().create(BUTTON_DISABLE_2);
    private final DigitalOutput washer1 = pi4j.digitalOutput().create(17);
    private final DigitalOutput washer2 = pi4j.digitalOutput().create(27);

    private boolean washer1state = false;
    private boolean washer2state = false;

    public void mainWashing(Integer washerNumber, String username, Long desiredBalance) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (user.getBalance() < desiredBalance) {
            throw new RuntimeException("Nincs elég pénz ehhez mosáshoz");
        }

        switch (washerNumber) {
            case WASHER_1 -> {
                if (washer1state) {
                    throw new RuntimeException("Ez a mosó már használatban van");
                }
                setWasher1state(true);
                user.setBalance(user.getBalance() - desiredBalance);
                washer1.high();
                buttonDisable1.high();
                Runnable task = () -> {
                    washer1state = false;
                    washer1.low();
                    buttonDisable1.low();
                };
                taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(desiredBalance)));
            }
            case WASHER_2 -> {
                if (washer2state) {
                    throw new RuntimeException("Ez a mosó már használatban van");
                }
                setWasher2state(true);
                user.setBalance(user.getBalance() - desiredBalance);
                washer2.high();
                buttonDisable2.high();
                Runnable task = () -> {
                    washer2state = false;
                    washer2.low();
                    buttonDisable2.low();
                };
                taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(desiredBalance)));
            }
        }
        userRepository.save(user);
    }
}