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

@RestController
@RequestMapping(value = "/api/mortgage-check")
@RequiredArgsConstructor
public class MortgageController {

    private final MortgageService mortgageService;

    @PostMapping
    public MortgageCheckDto checkMortgage(@RequestBody @Valid MortgageCheckRequest request) {
        return mortgageService.checkMortgage(request);
    }

}
