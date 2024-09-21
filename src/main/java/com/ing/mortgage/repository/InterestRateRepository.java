package com.ing.mortgage.repository;

import com.ing.mortgage.model.InterestRate;
import com.ing.mortgage.repository.exception.MaturityPeriodNotFoundException;
import com.ing.mortgage.repository.storage.InMemoryStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Repository for interest rates.
 */
@Component
@RequiredArgsConstructor
public class InterestRateRepository {

    private final InMemoryStorage storage;

    public List<InterestRate> getAllInterestRates() {
        return storage.getInterestRates();
    }

    public InterestRate getInterestRateForMaturity(int maturityPeriod) {
        return storage.getInterestRates().stream()
                .filter(rate -> rate.getMaturityPeriod() == maturityPeriod)
                .findFirst()
                .orElseThrow(() -> new MaturityPeriodNotFoundException("No interest rate found for maturity period: " + maturityPeriod));
    }

}
