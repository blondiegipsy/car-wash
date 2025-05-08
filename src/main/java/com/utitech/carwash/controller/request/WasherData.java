package com.utitech.carwash.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WasherData {
    private int washer1Time;
    private int washer2Time;
    private int vacuumTime;
    private int chassisWasherTime;
    private boolean washer1Ready;
    private boolean washer2Ready;
    private boolean vacuumReady;
    private boolean chassisWasherReady;
}