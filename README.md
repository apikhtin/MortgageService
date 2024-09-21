# Mortgage Service
This service provides a REST API to calculate the monthly payment of a mortgage and list all interest rates.

## Description
Developed using Spring Boot and Spring MVC, this service provides two endpoints:
- GET /api/interest-rates: List all interest rates
- POST /api/mortgage-check: Calculate the monthly payment of a mortgage

## Requirements
- Java 21
- Installed Maven

## How to run
1. Clone the repository
2. Run the command `mvn spring-boot:run`

## Assumptions:
- In memory storage for interest rates (hard-coded), not an in memory database
