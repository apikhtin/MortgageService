package com.ing.mortgage.service;

import com.ing.mortgage.controller.dto.MortgageCheckDto;
import com.ing.mortgage.controller.dto.request.MortgageCheckRequest;
import com.ing.mortgage.model.InterestRate;
import com.ing.mortgage.repository.InterestRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MortgageServiceTest {

    @Mock
    private InterestRateRepository interestRateRepository;

    @InjectMocks
    private MortgageService mortgageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckMortgage_Feasible() {
        // Given a feasible mortgage request
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(100000)); // High income
        request.setLoanValue(BigDecimal.valueOf(300000)); // Loan < income * 4
        request.setHomeValue(BigDecimal.valueOf(350000)); // Loan < home value
        request.setMaturityPeriod(10); // Valid maturity period

        InterestRate interestRate = new InterestRate(UUID.randomUUID(), 10, 3.5, Instant.now());
        when(interestRateRepository.getInterestRateForMaturity(10)).thenReturn(interestRate);

        // When
        MortgageCheckDto result = mortgageService.checkMortgage(request);

        // Then
        assertTrue(result.isFeasible(), "Mortgage should be feasible");
        assertTrue(result.getMonthlyCost().compareTo(BigDecimal.ZERO) > 0, "Monthly cost should be calculated");

        // Verify that the repository method was called
        verify(interestRateRepository, times(1)).getInterestRateForMaturity(10);
    }

    @Test
    void testCheckMortgage_NotFeasible_Income() {
        // Given a mortgage request where loan exceeds factored income
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000)); // Low income
        request.setLoanValue(BigDecimal.valueOf(300000)); // Loan > income * 4
        request.setHomeValue(BigDecimal.valueOf(350000)); // Loan < home value
        request.setMaturityPeriod(10); // Valid maturity period

        // When
        MortgageCheckDto result = mortgageService.checkMortgage(request);

        // Then
        assertFalse(result.isFeasible(), "Mortgage should not be feasible");
        assertEquals(BigDecimal.ZERO, result.getMonthlyCost(), "Monthly cost should be zero for non-feasible mortgage");

        // Verify that the repository method was never called (as mortgage is not feasible)
        verify(interestRateRepository, never()).getInterestRateForMaturity(anyInt());
    }

    @Test
    void testCheckMortgage_NotFeasible_HomeValue() {
        // Given a mortgage request where loan exceeds home value
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(100000)); // High income
        request.setLoanValue(BigDecimal.valueOf(400000)); // Loan <= income * 4
        request.setHomeValue(BigDecimal.valueOf(350000)); // Loan > home value
        request.setMaturityPeriod(10); // Valid maturity period

        // When
        MortgageCheckDto result = mortgageService.checkMortgage(request);

        // Then
        assertFalse(result.isFeasible(), "Mortgage should not be feasible");
        assertEquals(BigDecimal.ZERO, result.getMonthlyCost(), "Monthly cost should be zero for non-feasible mortgage");

        // Verify that the repository method was never called (as mortgage is not feasible)
        verify(interestRateRepository, never()).getInterestRateForMaturity(anyInt());
    }

    @Test
    void testCalculateMonthlyCost() {
        // Given a valid mortgage request
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setLoanValue(BigDecimal.valueOf(100000));
        request.setMaturityPeriod(10);
        request.setIncome(BigDecimal.valueOf(100000));
        request.setHomeValue(BigDecimal.valueOf(100000));

        InterestRate interestRate = new InterestRate(UUID.randomUUID(), 10, 3.5, Instant.now());
        when(interestRateRepository.getInterestRateForMaturity(10)).thenReturn(interestRate);

        // When
        BigDecimal monthlyCost = mortgageService.checkMortgage(request).getMonthlyCost();

        // Then
        assertTrue(monthlyCost.compareTo(BigDecimal.ZERO) > 0, "Monthly cost should be greater than zero");

        // Verify that the repository method was called
        verify(interestRateRepository, times(1)).getInterestRateForMaturity(10);
        assertEquals(BigDecimal.valueOf(29166.6666700013), monthlyCost);
    }
}
