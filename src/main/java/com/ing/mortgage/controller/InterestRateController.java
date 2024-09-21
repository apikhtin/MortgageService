package com.ing.mortgage.controller;

import com.ing.mortgage.controller.dto.InterestRateDto;
import com.ing.mortgage.controller.mapper.InterestRateMapper;
import com.ing.mortgage.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/interest-rates")
@RequiredArgsConstructor
public class InterestRateController {

    private final InterestRateService interestRateService;

    private final InterestRateMapper interestRateMapper;

    @GetMapping
    public List<InterestRateDto> getInterestRates() {
        var interestRates = interestRateService.getAllInterestRates();
        return interestRateMapper.toInterestRateResponses(interestRates);
    }

}
