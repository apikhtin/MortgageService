package com.ing.mortgage.controller;

import com.ing.mortgage.controller.dto.MortgageCheckDto;
import com.ing.mortgage.controller.dto.request.MortgageCheckRequest;
import com.ing.mortgage.service.MortgageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for mortgage checks.
 */
@RestController
@RequestMapping(value = "/api/mortgage-check")
@RequiredArgsConstructor
public class MortgageController {

    private final MortgageService mortgageService;

    /**
     * Check if a mortgage is feasible and calculate the monthly cost.
     *
     * @param request the mortgage check request
     * @return the mortgage check result
     */
    @PostMapping
    public MortgageCheckDto checkMortgage(@RequestBody @Valid MortgageCheckRequest request) {
        return mortgageService.checkMortgage(request);
    }

}
