package com.utitech.carwash.hardware;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class RelayContext {

    private final Context pi4j = Pi4J.newAutoContext();

    private final Map<Integer, DigitalOutput> pinOutputs = new HashMap<>();
    private final Map<Integer, Boolean> pinStates = new HashMap<>();

    private final Map<String, Integer> pinMap = Map.ofEntries(
            Map.entry("BUTTON_DISABLE_1", 23),
            Map.entry("BUTTON_DISABLE_2", 24),
            Map.entry("VACUUM", 5),
            Map.entry("BUTTON_VACUUM_DISABLE", 6),
            Map.entry("WASHER_1", 17),
            Map.entry("WASHER_2", 27),
            Map.entry("CHASSIS_WASHER", 21),
            Map.entry("CHASSIS_WASHER_PUMP", 20),
            Map.entry("CHASSIS_WASHER_VALVE", 19),
            Map.entry("LAMP_1_BUTTON", 26),
            Map.entry("LAMP_2_BUTTON", 16),
            Map.entry("LAMP_3_BUTTON", 18),
            Map.entry("LAMP_4_BUTTON", 22),
            Map.entry("LAMP_5_BUTTON", 25),
            Map.entry("LAMP_6_BUTTON", 12),
            Map.entry("LAMP_7_BUTTON", 13)
    );

    public int washer1Timer = 0;
    public int washer2Timer = 0;
    public int vacuumTimer = 0;
    public int chassisWasherTimer = 0;

    @PostConstruct
    private void init() {
        for (int pin : pinMap.values()) {
            DigitalOutput output = pi4j.digitalOutput().create(pin);
            output.low();
            pinOutputs.put(pin, output);
            pinStates.put(pin, false);
        }
    }

    @PreDestroy
    private void cleanup() {
        pinOutputs.values().forEach(DigitalOutput::low);
    }

    public void toggle(int pin) {
        var output = pinOutputs.get(pin);
        if (output == null) throw new IllegalArgumentException("Unknown pin: " + pin);

        boolean newState = !pinStates.get(pin);
        output.state(DigitalState.getState(newState)); // true = HIGH, false = LOW
        pinStates.put(pin, newState);
    }

    public boolean getState(int pin) {
        return pinStates.get(pin);
    }
}