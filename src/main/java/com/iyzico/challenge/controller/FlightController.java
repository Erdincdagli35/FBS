package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.service.FlightService;
import com.iyzico.challenge.service.SeatService;
import com.iyzico.challenge.validation.FlightValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing flight operations.
 * Provides endpoints to add, update, and delete flights.
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightRepository  flightRepository;
    private final FlightValidation flightValidation;

    /**
     * Adds a new flight.
     *
     * @param flight The flight details to be added.
     * @return The ID of the added flight.
     */
    @PostMapping
    public ResponseEntity addFlight(@RequestBody Flight flight) {
        log.info("Request received to add a flight: {}", flight);

        if(!flightValidation.validateFlightNumber(flight.getFlightNumber())){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(flightService.addFlight(flight));
    }

    /**
     * Retrieves a list of all flights.
     *
     * @return A list of all flights.
     */
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        log.info("Request received to retrieve all flights.");
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    /**
     * Deletes a flight by ID.
     *
     * @param flightId The ID of the flight to be deleted.
     * @return The ID of the deleted flight.
     */
    @DeleteMapping("/{flightId}")
    public ResponseEntity deleteFlight(@PathVariable Long flightId) {
        log.info("Request received to delete flight with ID: {}", flightId);
        return flightRepository.existsById(flightId) ? ResponseEntity.ok(flightService.deleteFlight(flightId)) : ResponseEntity.badRequest().build();
    }

    /**
     * Updates the details of an existing flight.
     *
     * @param flight The flight details to be updated.
     * @return The updated flight details.
     */
    @PutMapping
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) {
        log.info("Request received to update flight: {}", flight);
        if(!flightValidation.validateFlightNumber(flight.getFlightNumber())){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(flightService.updateFlight(flight));
    }
}
