package com.ing.mortgage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestRateDto {

    private int maturityPeriod;

    private double interestRate;

    private Instant lastUpdate;

}
