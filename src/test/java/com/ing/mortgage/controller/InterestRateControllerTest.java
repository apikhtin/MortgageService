package com.ing.mortgage.controller;

import com.ing.mortgage.controller.dto.InterestRateDto;
import com.ing.mortgage.controller.mapper.InterestRateMapper;
import com.ing.mortgage.model.InterestRate;
import com.ing.mortgage.service.InterestRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(InterestRateController.class)
public class InterestRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterestRateService interestRateService;

    @MockBean
    private InterestRateMapper interestRateMapper;

    private List<InterestRate> mockInterestRates;
    private List<InterestRateDto> mockInterestRateDtos;

    @BeforeEach
    public void setUp() {
        // Set up mock InterestRates
        Instant now = Instant.now();
        mockInterestRates = List.of(
                new InterestRate(UUID.randomUUID(), 12, 3.5, now),
                new InterestRate(UUID.randomUUID(), 24, 4.2, now)
        );

        // Set up mock InterestRateDtos
        mockInterestRateDtos = List.of(
                new InterestRateDto(12, 3.5, now),
                new InterestRateDto(24, 4.2, now)
        );
    }

    @Test
    public void testGetInterestRates() throws Exception {
        // Mock the service and mapper responses
        when(interestRateService.getAllInterestRates()).thenReturn(mockInterestRates);
        when(interestRateMapper.toInterestRateResponses(mockInterestRates)).thenReturn(mockInterestRateDtos);

        // Perform GET request and verify response
        mockMvc.perform(get("/api/interest-rates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maturityPeriod", is(12)))
                .andExpect(jsonPath("$[0].interestRate", is(3.5)))
                .andExpect(jsonPath("$[0].lastUpdate", is(mockInterestRateDtos.get(0).getLastUpdate().toString())))
                .andExpect(jsonPath("$[1].maturityPeriod", is(24)))
                .andExpect(jsonPath("$[1].interestRate", is(4.2)))
                .andExpect(jsonPath("$[1].lastUpdate", is(mockInterestRateDtos.get(1).getLastUpdate().toString())));
    }
}
