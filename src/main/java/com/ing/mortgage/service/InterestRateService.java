package com.ing.mortgage.service;

import com.ing.mortgage.model.InterestRate;
import com.ing.mortgage.repository.InterestRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for interest rates.
 */
@Service
@RequiredArgsConstructor
public class InterestRateService {

    private final InterestRateRepository interestRateRepository;

    /**
     * Get all interest rates.
     *
     * @return the list of interest rates
     */
    public List<InterestRate> getAllInterestRates() {
        return interestRateRepository.getAllInterestRates();
    }

}
