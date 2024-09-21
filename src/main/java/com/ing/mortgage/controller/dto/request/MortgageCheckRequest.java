package com.ing.mortgage.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MortgageCheckRequest {

    @DecimalMin(value = "0.0", inclusive = false, message = "Income must be greater than 0")
    private BigDecimal income;

    @Positive(message = "Maturity period must be greater than 0")
    private int maturityPeriod;

    @DecimalMin(value = "0.0", inclusive = false,  message = "Loan value must be greater than 0")
    private BigDecimal loanValue;

    @DecimalMin(value = "0.0", message = "Home value must be greater than 0")
    private BigDecimal homeValue;

}
