package com.utitech.carwash.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tariffs {

    @Id
    private int id;
    private int washingTime;
    private int vacuumTime;
    private int chassisWashingTime;
}
