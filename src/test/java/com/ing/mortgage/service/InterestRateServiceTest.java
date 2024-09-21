package com.ing.mortgage.service;

import com.ing.mortgage.model.InterestRate;
import com.ing.mortgage.repository.InterestRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InterestRateServiceTest {

    @Mock
    private InterestRateRepository interestRateRepository;

    @InjectMocks
    private InterestRateService interestRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data for testing
        List<InterestRate> interestRates = List.of(
                new InterestRate(UUID.randomUUID(), 1, 4.22, Instant.parse("2021-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(), 2, 4.32, Instant.parse("2023-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(), 3, 4.43, Instant.parse("2024-01-01T00:00:00Z"))
        );

        // Mocking the behavior of InterestRateRepository
        when(interestRateRepository.getAllInterestRates()).thenReturn(interestRates);
    }

    @Test
    void testGetAllInterestRates() {
        // Call the method to be tested
        List<InterestRate> result = interestRateService.getAllInterestRates();

        // Verify that the repository method was called once
        verify(interestRateRepository, times(1)).getAllInterestRates();

        // Assert the size of the list
        assertEquals(3, result.size());

        // Assert the details of the interest rates
        assertEquals(1, result.get(0).getMaturityPeriod());
        assertEquals(4.22, result.get(0).getInterestRate());

        assertEquals(2, result.get(1).getMaturityPeriod());
        assertEquals(4.32, result.get(1).getInterestRate());

        assertEquals(3, result.get(2).getMaturityPeriod());
        assertEquals(4.43, result.get(2).getInterestRate());
    }
}

