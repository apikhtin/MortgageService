package com.ing.mortgage.controller.advice;

import com.ing.mortgage.repository.exception.MaturityPeriodNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    public GlobalExceptionHandlerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleMaturityPeriodException() {
        // Simulate MaturityPeriodNotFoundException handling
        MaturityPeriodNotFoundException exception = new MaturityPeriodNotFoundException("Maturity period not found");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<String> response = globalExceptionHandler.handleMaturityPeriodException(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Maturity period not found", response.getBody());
    }

    @Test
    public void testHandleValidationExceptions() {
        // Mock the MethodArgumentNotValidException and BindingResult
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        // Create field errors for income and maturityPeriod
        FieldError incomeError = new FieldError("mortgageCheckRequest", "income", "Income must be greater than 0");
        FieldError maturityPeriodError = new FieldError("mortgageCheckRequest", "maturityPeriod", "Maturity period must be greater than 0");

        // Mock the behavior of getBindingResult to return the mocked BindingResult
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // Mock the behavior of getAllErrors to return the list of field errors
        when(bindingResult.getAllErrors()).thenReturn(List.of(incomeError, maturityPeriodError));

        // Call the exception handler method
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Income must be greater than 0", response.getBody().get("income"));
        assertEquals("Maturity period must be greater than 0", response.getBody().get("maturityPeriod"));
    }

    @Test
    public void testHandleGlobalException() {
        // Simulate a general exception
        Exception exception = new Exception("General error");
        WebRequest webRequest = mock(WebRequest.class);

        ResponseEntity<String> response = globalExceptionHandler.handleGlobalException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: General error", response.getBody());
    }
}
