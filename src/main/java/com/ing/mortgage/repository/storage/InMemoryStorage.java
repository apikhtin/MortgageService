package com.ing.mortgage.repository.storage;

import com.ing.mortgage.model.InterestRate;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Component
public class InMemoryStorage {

    private List<InterestRate> interestRates;

    @PostConstruct
    public void init() {
        this.interestRates = List.of(
                new InterestRate(UUID.randomUUID(),1, 4.22, Instant.parse("2021-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(), 2, 4.32, Instant.parse("2023-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),3, 4.43, Instant.parse("2024-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),4, 4.54, Instant.parse("2022-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),5, 4.65, Instant.parse("2024-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),6, 4.16, Instant.parse("2023-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),7, 4.07, Instant.parse("2024-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),8, 4.01, Instant.parse("2022-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),9, 4.00, Instant.parse("2023-01-01T00:00:00Z")),
                new InterestRate(UUID.randomUUID(),10, 3.98, Instant.parse("2024-01-01T00:00:00Z"))
        );
    }
}
