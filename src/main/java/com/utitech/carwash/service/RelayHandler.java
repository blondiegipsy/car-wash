package com.utitech.carwash.service;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.utitech.carwash.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Getter
@Setter
@Service
public class RelayHandler {
    private final Context pi4j = Pi4J.newAutoContext();
    private final TaskScheduler taskScheduler;
    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final TariffsRepository tariffsRepository;

    private static final int BUTTON_DISABLE_1 = 23;
    private static final int BUTTON_DISABLE_2 = 24;
    private static final int WASHER_1 = 17;
    private static final int WASHER_2 = 27;
    private static final int VACUUM = 5;
    private static final int BUTTON_VACUUM_DISABLE = 6;
    private static final int LAMP_1_BUTTON = 26;
    private static final int LAMP_2_BUTTON = 16;

    private final DigitalOutput buttonDisable1 = pi4j.digitalOutput().create(BUTTON_DISABLE_1);
    private final DigitalOutput buttonDisable2 = pi4j.digitalOutput().create(BUTTON_DISABLE_2);
    private final DigitalOutput vacuumButtonDisable = pi4j.digitalOutput().create(BUTTON_VACUUM_DISABLE);
    private final DigitalOutput washer1 = pi4j.digitalOutput().create(WASHER_1);
    private final DigitalOutput washer2 = pi4j.digitalOutput().create(WASHER_2);
    private final DigitalOutput vacuum = pi4j.digitalOutput().create(VACUUM);
    private final DigitalOutput lamp1 = pi4j.digitalOutput().create(LAMP_1_BUTTON);
    private final DigitalOutput lamp2 = pi4j.digitalOutput().create(LAMP_2_BUTTON);

    private boolean washer1state = false;
    private boolean washer2state = false;
    private boolean vacuumState = false;
    private boolean lamp1state = false;
    private boolean lamp2state = false;

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
                createLog(username, desiredBalance.intValue(), "Mosó 1");
                Runnable task = () -> {
                    washer1state = false;
                    washer1.low();
                    buttonDisable1.low();
                };
                Integer duration = tariffsRepository.findAll().getFirst().getSecondForWashing();
                taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(desiredBalance * duration)));
            }
            case WASHER_2 -> {
                if (washer2state) {
                    throw new RuntimeException("Ez a mosó már használatban van");
                }
                setWasher2state(true);
                user.setBalance(user.getBalance() - desiredBalance);
                washer2.high();
                buttonDisable2.high();
                createLog(username, desiredBalance.intValue(), "Mosó 2");
                Runnable task = () -> {
                    washer2state = false;
                    washer2.low();
                    buttonDisable2.low();
                };
                Integer duration = tariffsRepository.findAll().getFirst().getSecondForWashing();
                taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(desiredBalance * duration)));
            }
            case VACUUM -> {
                if (vacuumState) {
                    throw new RuntimeException("Porszívó már használatban van");
                }
                setVacuumState(true);
                user.setBalance(user.getBalance() - desiredBalance);
                vacuum.high();
                vacuumButtonDisable.high();
                createLog(username, desiredBalance.intValue(), "Porszivó");
                Runnable task = () -> {
                    vacuumState = false;
                    vacuum.low();
                    vacuumButtonDisable.low();
                };
                Integer duration = tariffsRepository.findAll().getFirst().getSecondForVacuuming();
                taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(desiredBalance * duration)));
            }
        }
        userRepository.save(user);
    }

    public void toggleLamp(Integer lamp) {
        if (lamp == LAMP_1_BUTTON) {
            lamp1.toggle();
            lamp1state ^= true;
        } else {
            lamp2.toggle();
            lamp2state ^= true;
        }
    }

    private void createLog(String username, Integer desiredBalance, String service) {
        Log log = new Log();
        log.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.setUsername(username);
        log.setAmountUsed(desiredBalance);
        log.setService(service);
        logRepository.save(log);
    }

    public Map<String, Boolean> getStates() {
        Map<String, Boolean> states = new HashMap<>();
        states.put("washer1state", washer1state);
        states.put("washer2state", washer2state);
        states.put("vacuumState", vacuumState);
        states.put("lamp1state", lamp1state);
        states.put("lamp2state", lamp2state);
        return states;
    }
}