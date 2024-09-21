package com.ing.mortgage.repository.exception;

public class MaturityPeriodNotFoundException extends RuntimeException {
    public MaturityPeriodNotFoundException(String message) {
        super(message);
    }
}
