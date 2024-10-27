package com.utitech.carwash.raspi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
@Getter
public class RelayHandler {
    private final Context pi4j = Pi4J.newAutoContext();
    private final TaskScheduler taskScheduler;

    private static final int BUTTON_DISABLE_1 = 23;
    private static final int BUTTON_DISABLE_2 = 24;
    private static final int WASHER_1 = 17;
    private static final int WASHER_2 = 27;

    private final DigitalOutput buttonDisable1 = pi4j.digitalOutput().create(BUTTON_DISABLE_1);
    private final DigitalOutput buttonDisable2 = pi4j.digitalOutput().create(BUTTON_DISABLE_2);
    private final DigitalOutput washerStart1 = pi4j.digitalOutput().create(17);
    private final DigitalOutput washerStart2 = pi4j.digitalOutput().create(27);

    private boolean washer1state;
    private boolean washer2state;

    public void startWasher(int washerNumber, int balance, int disableRelay) throws InterruptedException {
        if (washerStart1.state().isHigh()) {
            throw new RuntimeException("Washer " + washerNumber + " is high");
        }
        washer1state = true;
        washerStart1.high();
        buttonDisable1.high();
        timerWasher(balance);
    }

    private void timerWasher(int balance) {
        Runnable task = () -> {
            washer1state = false;
            washerStart1.low();
            buttonDisable1.low();
        };
        taskScheduler.schedule(task, Instant.now().plus(Duration.ofSeconds(balance)));
    }
}