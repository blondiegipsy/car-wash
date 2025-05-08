package com.utitech.carwash.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelayStatesRequest {
    private boolean washer1State;
    private boolean washer2State;
    private boolean vacuumState;
    private boolean chassisWasherState;
    private boolean chassisPumpState;
    private boolean chassisValveState;
    private boolean lamp1State;
    private boolean lamp2State;
    private boolean lamp3State;
    private boolean lamp4State;
    private boolean lamp5State;
    private boolean lamp6State;
    private boolean lamp7State;
}
