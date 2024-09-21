package com.ing.mortgage.repository;

import com.ing.mortgage.model.InterestRate;
import com.ing.mortgage.repository.exception.MaturityPeriodNotFoundException;
import com.ing.mortgage.repository.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterestRateRepositoryTest {

    @Mock
    private InMemoryStorage inMemoryStorage;

    @InjectMocks
    private InterestRateRepository interestRateRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data for testing
        List<InterestRate> interestRates = List.of(
                new InterestRate(UUID.randomUUID(), 1, 4.22, Instant.parse("2021-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(), 2, 4.32, Instant.parse("2023-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(), 3, 4.43, Instant.parse("2024-01-01T00:00:00Z"))
        );

        // Mocking the behavior of InMemoryStorage
        when(inMemoryStorage.getInterestRates()).thenReturn(interestRates);
    }

    @Test
    void testGetAllInterestRates() {
        // Call the method to be tested
        List<InterestRate> result = interestRateRepository.getAllInterestRates();

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

    @Test
    void testGetInterestRateForMaturityValid() {
        // Call the method with a valid maturity period
        InterestRate result = interestRateRepository.getInterestRateForMaturity(2);

        // Assert that the correct interest rate is returned
        assertNotNull(result);
        assertEquals(2, result.getMaturityPeriod());
        assertEquals(4.32, result.getInterestRate());
    }

    @Test
    void testGetInterestRateForMaturityNotFound() {
        // Call the method with an invalid maturity period and expect an exception
        Exception exception = assertThrows(MaturityPeriodNotFoundException.class,
                () -> interestRateRepository.getInterestRateForMaturity(5));

        // Assert the exception message
        String expectedMessage = "No interest rate found for maturity period: 5";
        assertEquals(expectedMessage, exception.getMessage());
    }
}

