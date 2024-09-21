package com.ing.mortgage.service;

import com.ing.mortgage.controller.dto.MortgageCheckDto;
import com.ing.mortgage.controller.dto.request.MortgageCheckRequest;
import com.ing.mortgage.repository.InterestRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service for mortgage checks.
 */
@Service
@RequiredArgsConstructor
public class MortgageService {

    public static final BigDecimal LOAN_INCOME_FACTOR = BigDecimal.valueOf(4);

    private final InterestRateRepository interestRateRepository;

    /**
     * Check if a mortgage is feasible and calculate the monthly cost.
     *
     * @param request the mortgage check request
     * @return the mortgage check result
     */
    public MortgageCheckDto checkMortgage(MortgageCheckRequest request) {

        var result = new MortgageCheckDto();

        var feasible = loanLessFactoredIncome(request) && loanExceedsHomeValue(request);
        result.setFeasible(feasible);

        var monthlyCost = feasible ? calculateMonthlyCost(request) : BigDecimal.ZERO;
        result.setMonthlyCost(monthlyCost);

        return result;
    }

    private BigDecimal calculateMonthlyCost(MortgageCheckRequest request) {
        var interestRate =  interestRateRepository.getInterestRateForMaturity(request.getMaturityPeriod());
        return doCalculateMonthlyCost(request.getLoanValue(), interestRate.getInterestRate(), request.getMaturityPeriod());
    }

    private static BigDecimal doCalculateMonthlyCost(BigDecimal principal, double annualInterestRate, int years) {
        // Convert double annualInterestRate to BigDecimal
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRate).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Total number of payments (months)
        int totalPayments = years * 12;

        // (1 + monthlyInterestRate) ^ totalPayments
        BigDecimal onePlusRateToPower = monthlyInterestRate.add(BigDecimal.ONE).pow(totalPayments);

        // numerator: principal * monthlyInterestRate * (1 + monthlyInterestRate) ^ totalPayments
        BigDecimal numerator = principal.multiply(monthlyInterestRate).multiply(onePlusRateToPower);

        // denominator: (1 + monthlyInterestRate) ^ totalPayments - 1
        BigDecimal denominator = onePlusRateToPower.subtract(BigDecimal.ONE);

        // monthly payment = numerator / denominator
        return numerator.divide(denominator, 10, RoundingMode.HALF_UP);
    }

    private static boolean loanLessFactoredIncome(MortgageCheckRequest request) {
        var maxMortgage = request.getIncome().multiply(LOAN_INCOME_FACTOR);
        return request.getLoanValue().compareTo(maxMortgage) <= 0;
    }

    private static boolean loanExceedsHomeValue(MortgageCheckRequest request) {
        return request.getLoanValue().compareTo(request.getHomeValue()) <= 0;
    }

}
