package com.ing.mortgage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InterestRate {

    private UUID id;

    private int maturityPeriod;

    private double interestRate;

    private Instant lastUpdate;

}
