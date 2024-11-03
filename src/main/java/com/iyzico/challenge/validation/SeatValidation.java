package com.iyzico.challenge.validation;

import com.iyzico.challenge.service.implementation.FlightServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@AllArgsConstructor
public class SeatValidation {

    private final FlightServiceImp flightServiceImp;

    public boolean validateSeatNumber(List<String> seatNumbers) {
        HashSet<String> validSeatNumbers = flightServiceImp.generateFlightSeatNumbers();

        for (String seat : seatNumbers) {
            if (!validSeatNumbers.contains(seat)) {
                return false;
            }
        }
        return true;
    }
}
