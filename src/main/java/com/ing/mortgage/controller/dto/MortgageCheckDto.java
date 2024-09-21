package com.ing.mortgage.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MortgageCheckDto {

    private boolean feasible;

    private BigDecimal monthlyCost;

}
