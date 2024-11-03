package com.iyzico.challenge.service.implementation;

import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.entity.SeatStatus;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.repository.SeatRepository;
import com.iyzico.challenge.service.FlightService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
@Transactional
public class FlightServiceImp implements FlightService {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    /**
     * Adds a new flight along with generating its seats.
     *
     * @param flight The flight to be added.
     * @return The ID of the saved flight.
     */
    @Override
    public Long addFlight(Flight flight) {
        log.info("Adding flight: {}", flight);

        // Generate seats for the flight
        List<Seat> seats = new ArrayList<>();
        List<String> seatNames = new ArrayList<>(generateFlightSeatNumbers());

        for (String seatName : seatNames) {
            Seat seat = new Seat();
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setSeatNumber(seatName);
            seat.setFlight(flight);
            seats.add(seat);
        }

        flight.setSeats(seats);

        // Save flight with seats
        Flight savedFlight = flightRepository.save(flight);
        log.info("Flight added successfully with ID: {}", savedFlight.getId());
        return savedFlight.getId();
    }

    /**
     * Retrieves all flights.
     *
     * @return A list of all flights.
     */
    @Override
    public List<Flight> getAllFlights() {
        log.info("Retrieving all flights.");
        return flightRepository.findAll();
    }

    /**
     * Retrieves a flight by its ID.
     *
     * @param flightId The ID of the flight.
     * @return The flight with the specified ID.
     */
    @Override
    public Flight getFlightById(Long flightId) {
        log.info("Retrieving flight with ID: {}", flightId);
        return flightRepository.findOneById(flightId);
    }

    /**
     * Deletes a flight by its ID.
     *
     * @param id The ID of the flight to be deleted.
     * @return The ID of the deleted flight.
     */
    @Override
    public Long deleteFlight(Long id) {
        log.info("Deleting flight with ID: {}", id);
        //Firstly, We gotta delete seats by flight id
        List<Seat> seats = seatRepository.findAllByFlight_IdOrderBySeatNumberAsc(id);
        seatRepository.deleteAll(seats);

        Flight flight = getFlightById(id);
        flightRepository.delete(flight);
        log.info("Flight deleted successfully with ID: {}", id);
        return id;
    }

    /**
     * Updates the details of an existing flight.
     *
     * @param flight The flight details to be updated.
     * @return The updated flight entity.
     */
    @Override
    public Flight updateFlight(Flight flight) {
        log.info("Updating flight with ID: {}", flight.getId());
        Flight existingFlight = getFlightById(flight.getId());

        existingFlight.setFlightNumber(flight.getFlightNumber());
        existingFlight.setOrigin(flight.getOrigin());
        existingFlight.setDestination(flight.getDestination());
        existingFlight.setDepartureTime(flight.getDepartureTime());
        existingFlight.setPrice(flight.getPrice());

        Flight updatedFlight = flightRepository.save(existingFlight);
        log.info("Flight updated successfully with ID: {}", updatedFlight.getId());

        return updatedFlight;
    }

    /**
     * Generates a set of seat numbers for a flight.
     *
     * @return A set of unique seat numbers.
     */
    public HashSet<String> generateFlightSeatNumbers() {
        HashSet<String> seatNumbers = new HashSet<>();

        for (int row = 1; row <= 30; row++) {
            for (char column : new char[]{'A', 'B', 'C', 'D', 'E', 'F'}) {
                seatNumbers.add(column + String.valueOf(row));
            }
        }

        return seatNumbers;
    }
}
