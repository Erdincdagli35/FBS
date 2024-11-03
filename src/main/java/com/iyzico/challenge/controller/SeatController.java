package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.repository.SeatRepository;
import com.iyzico.challenge.response.FlightSeatResponse;
import com.iyzico.challenge.service.SeatService;
import com.iyzico.challenge.validation.SeatValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing seat operations.
 * Provides endpoints to add, retrieve, update, and delete seats for flights.
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;
    private final SeatRepository seatRepository;
    private final SeatValidation seatValidation;

    /**
     * Adds seats to a specific flight.
     *
     * @param seatNumbers List of seat numbers to be added.
     * @param flightId    ID of the flight to which seats are added.
     * @return A response with the result of the seat addition.
     */
    @PostMapping("/{flightId}")
    public ResponseEntity<?> addSeat(@RequestBody List<String> seatNumbers, @PathVariable Long flightId) {
        log.info("Adding seats to flight with ID {}: {}", flightId, seatNumbers);

        if (!seatValidation.validateSeatNumber(seatNumbers)){
            return ResponseEntity.badRequest().body("Seat number is invalid");
        }

        return ResponseEntity.ok(seatService.addSeat(seatNumbers, flightId));
    }

    /**
     * Retrieves a list of all seats with flight details.
     *
     * @return A list of all seats with flight details.
     */
    @GetMapping
    public ResponseEntity<List<FlightSeatResponse>> getAllSeats() {
        log.info("Retrieving all seats with flight details.");
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    /**
     * Retrieves a seat by its ID.
     *
     * @param seatId The ID of the seat to be retrieved.
     * @return The seat with the specified ID.
     */
    @GetMapping("/{seatId}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long seatId) {
        log.info("Retrieving seat with ID: {}", seatId);
        return ResponseEntity.ok(seatService.getSeatById(seatId));
    }

    /**
     * Deletes a seat by its ID.
     *
     * @param seatId The ID of the seat to be deleted.
     * @return A response indicating the deletion result.
     */
    @DeleteMapping("/{seatId}")
    public ResponseEntity<Long> deleteSeat(@PathVariable Long seatId) {
        log.info("Deleting seat with ID: {}", seatId);
        return seatRepository.existsById(seatId) ? ResponseEntity.ok(seatService.deleteSeat(seatId)) : ResponseEntity.badRequest().build();
    }

    /**
     * Updates the details of an existing seat.
     *
     * @param seat The seat details to be updated.
     * @return The updated seat entity.
     */
    @PutMapping
    public ResponseEntity updateSeat(@RequestBody Seat seat) {
        log.info("Updating seat with ID: {}", seat.getId());
        List<String> seatNumbers = List.of(seat.getSeatNumber());

        if (!seatValidation.validateSeatNumber(seatNumbers)){
            return ResponseEntity.badRequest().body("Seat number is invalid");
        }

        return ResponseEntity.ok(seatService.updateSeat(seat));
    }
}
