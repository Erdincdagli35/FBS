package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Flight;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    Long addFlight(Flight flight);

    List<Flight> getAllFlights();

    Flight getFlightById(Long flightId);

    Long deleteFlight(Long id);

    Flight updateFlight(Flight flight);
}
