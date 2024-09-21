package com.ing.mortgage.controller.mapper;

import com.ing.mortgage.controller.dto.InterestRateDto;
import com.ing.mortgage.model.InterestRate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterestRateMapper {

    List<InterestRateDto> toInterestRateResponses(List<InterestRate> interestRates);

}
