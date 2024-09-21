package com.ing.mortgage.controller.mapper;

import com.ing.mortgage.controller.dto.InterestRateDto;
import com.ing.mortgage.model.InterestRate;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InterestRateMapperTest {

    // Get the mapper instance
    private final InterestRateMapper interestRateMapper = Mappers.getMapper(InterestRateMapper.class);

    @Test
    public void testToInterestRateResponses() {
        // Prepare test data
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Instant now = Instant.now();

        InterestRate interestRate1 = new InterestRate(id1, 12, 3.5, now);
        InterestRate interestRate2 = new InterestRate(id2, 24, 4.2, now);

        List<InterestRate> interestRates = List.of(interestRate1, interestRate2);

        // Call the mapper
        List<InterestRateDto> dtos = interestRateMapper.toInterestRateResponses(interestRates);

        // Assert size of the mapped list
        assertEquals(2, dtos.size());

        // Assert individual fields mapping for first element
        InterestRateDto dto1 = dtos.getFirst();
        assertEquals(12, dto1.getMaturityPeriod());
        assertEquals(3.5, dto1.getInterestRate());
        assertEquals(now, dto1.getLastUpdate());

        // Assert individual fields mapping for second element
        InterestRateDto dto2 = dtos.get(1);
        assertEquals(24, dto2.getMaturityPeriod());
        assertEquals(4.2, dto2.getInterestRate());
        assertEquals(now, dto2.getLastUpdate());
    }

    @Test
    public void testEmptyListMapping() {
        List<InterestRateDto> dtos = interestRateMapper.toInterestRateResponses(List.of());
        assertEquals(0, dtos.size());
    }

    @Test
    public void testNullMapping() {
        List<InterestRateDto> dtos = interestRateMapper.toInterestRateResponses(null);
        assertNull(dtos);
    }
}
