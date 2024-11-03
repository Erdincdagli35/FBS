package com.iyzico.challenge.validation;

import org.springframework.stereotype.Component;

@Component
public class FlightValidation {

    public boolean validateFlightNumber(String flightNumber) {
        return flightNumber.length() >= 2
                && Character.isLetter(flightNumber.charAt(0))
                && Character.isLetter(flightNumber.charAt(1));
    }
}
