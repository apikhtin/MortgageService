package com.ing.mortgage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.mortgage.controller.dto.MortgageCheckDto;
import com.ing.mortgage.controller.dto.request.MortgageCheckRequest;
import com.ing.mortgage.service.MortgageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MortgageController.class)
public class MortgageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MortgageService mortgageService;

    @Autowired
    private ObjectMapper objectMapper;

    private MortgageCheckRequest validRequest;
    private MortgageCheckDto mockResponse;

    @BeforeEach
    public void setUp() {
        // Set up valid mortgage request
        validRequest = new MortgageCheckRequest();
        validRequest.setIncome(new BigDecimal("5000"));
        validRequest.setMaturityPeriod(30);
        validRequest.setLoanValue(new BigDecimal("200000"));
        validRequest.setHomeValue(new BigDecimal("250000"));

        // Set up mock service response
        mockResponse = new MortgageCheckDto();
        mockResponse.setFeasible(true);
        mockResponse.setMonthlyCost(new BigDecimal("180000"));
    }

    @Test
    public void testCheckMortgage_ValidRequest() throws Exception {
        // Mock the service response for a valid request
        when(mortgageService.checkMortgage(validRequest)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feasible", is(true)))
                .andExpect(jsonPath("$.monthlyCost", is(180000)));
    }

    @Test
    public void testCheckMortgage_InvalidRequest_IncomeNegative() throws Exception {
        // Invalid request with negative income
        MortgageCheckRequest invalidRequest = new MortgageCheckRequest();
        invalidRequest.setIncome(new BigDecimal("-5000"));
        invalidRequest.setMaturityPeriod(30);
        invalidRequest.setLoanValue(new BigDecimal("200000"));
        invalidRequest.setHomeValue(new BigDecimal("250000"));

        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.income", is("Income must be greater than 0")));
    }

    @Test
    public void testCheckMortgage_InvalidRequest_MaturityPeriodZero() throws Exception {
        // Invalid request with maturity period set to 0
        MortgageCheckRequest invalidRequest = new MortgageCheckRequest();
        invalidRequest.setIncome(new BigDecimal("5000"));
        invalidRequest.setMaturityPeriod(0);
        invalidRequest.setLoanValue(new BigDecimal("200000"));
        invalidRequest.setHomeValue(new BigDecimal("250000"));

        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.maturityPeriod", is("Maturity period must be greater than 0")));
    }

    @Test
    public void testCheckMortgage_InvalidRequest_LoanValueZero() throws Exception {
        // Invalid request with loan value set to 0
        MortgageCheckRequest invalidRequest = new MortgageCheckRequest();
        invalidRequest.setIncome(new BigDecimal("5000"));
        invalidRequest.setMaturityPeriod(30);
        invalidRequest.setLoanValue(new BigDecimal("0"));
        invalidRequest.setHomeValue(new BigDecimal("250000"));

        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.loanValue", is("Loan value must be greater than 0")));
    }
}
