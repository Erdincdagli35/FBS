package com.iyzico.challenge.service.implementation;

import com.iyzico.challenge.entity.*;
import com.iyzico.challenge.repository.BookingRepository;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.repository.SeatRepository;
import com.iyzico.challenge.response.FlightSeatResponse;
import com.iyzico.challenge.service.FlightService;
import com.iyzico.challenge.service.IyzicoPaymentService;
import com.iyzico.challenge.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing seat operations.
 */
@Log4j2
@AllArgsConstructor
@Service
@Transactional
public class SeatServiceImp implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    private final FlightService flightService;
    private final IyzicoPaymentService iyzicoPaymentService;
    private final BookingRepository bookingRepository;

    /**
     * Adds a list of seats to a specific flight by updating seat statuses.
     *
     * @param seatNumbers The list of seat numbers to be booked.
     * @param flightId    The ID of the flight for which seats are being booked.
     * @return The list of seat numbers that were successfully booked.
     */
    @Override
    public List<String> addSeat(List<String> seatNumbers, Long flightId) {
        log.info("Booking seats {} for flight ID {}", seatNumbers, flightId);

        // Retrieve flight by ID, throw an exception if not found
        Flight flight = flightRepository.findOneById(flightId);
        BigDecimal totalPrice = BigDecimal.ZERO;

        List<Seat> seatList = new ArrayList<>();
        List<String> seatNumbersList = new ArrayList<>();

        for (String seatNumber : seatNumbers) {
            Seat seat = seatRepository.findOneByFlight_IdAndSeatNumber(flightId, seatNumber);
            if (seat == null) {
                throw new IllegalArgumentException("Seat not found for seat number: " + seatNumber);
            }

            // Calculate the total price for all seats
            totalPrice = totalPrice.add(BigDecimal.valueOf(flight.getPrice()));

            seat.setStatus(SeatStatus.RESERVED);
            seat.setFlight(flight);
            Seat savedSeat = seatRepository.save(seat);
            seatList.add(savedSeat);
            seatNumbersList.add(seatNumber);
        }

        if (!seatList.isEmpty()) {
            flight.setSeats(seatList);
            flightRepository.save(flight);
        }

        iyzicoPaymentService.pay(totalPrice);

        for (Seat seat : seatList) {
            addBooking(seat);
        }

        return seatNumbersList;
    }

    public void addBooking(Seat seat) {
        try {
            Booking booking = new Booking();

            switch (seat.getStatus()) {
                case BOOKED:
                    throw new IllegalStateException("Seat is already booked: " + seat.getSeatNumber());
                case AVAILABLE:
                    throw new IllegalStateException("Seat is available, reservation cannot be made: " + seat.getSeatNumber());
                case RESERVED:
                    booking.setFlight(seat.getFlight());
                    booking.setSeat(seat);
                    booking.setDescription(seat.getFlight().getFlightNumber() + "->" + seat.getSeatNumber());

                    bookingRepository.save(booking);

                    seat.setStatus(SeatStatus.BOOKED);
                    seatRepository.save(seat);

                    log.info("Reservation and payment completed successfully for seat: {}", seat.getSeatNumber());
                    break;
                default:
                    throw new IllegalStateException("Unknown seat status: " + seat.getStatus());
            }
        } catch (Exception e) {
            log.error("An error occurred during the reservation: {}", e.getMessage(), e);
            throw new RuntimeException("Reservation process failed for seat: " + seat.getSeatNumber(), e);
        }
    }

    /**
     * Retrieves all seats along with their flight details.
     *
     * @return A list of FlightSeatResponse objects containing seat and flight details.
     */
    @Override
    public List<FlightSeatResponse> getAllSeats() {
        log.info("Retrieving all seats with flight details.");
        List<Flight> flights = flightService.getAllFlights();
        List<FlightSeatResponse> seatResponses = new ArrayList<>();

        for (Flight flight : flights) {
            List<Seat> seats = seatRepository.findAllByFlight_IdOrderBySeatNumberAsc(flight.getId());

            for (Seat seat : seats) {
                if (seat.getStatus() == SeatStatus.AVAILABLE) {
                    FlightSeatResponse response = new FlightSeatResponse();
                    response.setFlightName(flight.getFlightNumber());
                    response.setDescription(flight.getOrigin() + " -> " + flight.getDestination());
                    response.setSeatNumber(seat.getSeatNumber());
                    response.setPrice(flight.getPrice());
                    response.setSeatStatus(seat.getStatus());
                    seatResponses.add(response);
                }
            }
        }

        return seatResponses;
    }

    /**
     * Retrieves a seat by its ID.
     *
     * @param seatId The ID of the seat to retrieve.
     * @return The seat with the specified ID.
     */
    @Override
    public Seat getSeatById(Long seatId) {
        log.info("Retrieving seat with ID: {}", seatId);
        return seatRepository.findOneById(seatId);
    }

    /**
     * Deletes a seat by its ID.
     *
     * @param seatId The ID of the seat to delete.
     * @return The ID of the deleted seat.
     */
    @Override
    public Long deleteSeat(Long seatId) {
        log.info("Deleting seat with ID: {}", seatId);
        Seat seat = getSeatById(seatId);
        seatRepository.delete(seat);
        log.info("Seat deleted successfully with ID: {}", seatId);
        return seatId;
    }

    /**
     * Updates the details of an existing seat.
     *
     * @param seat The seat entity with updated details.
     * @return The updated seat entity.
     */
    @Override
    public Seat updateSeat(Seat seat) {
        log.info("Updating seat with ID: {}", seat.getId());
        Seat existingSeat = getSeatById(seat.getId());

        existingSeat.setSeatNumber(seat.getSeatNumber());
        existingSeat.setStatus(seat.getStatus());
        existingSeat.setFlight(seat.getFlight());
        existingSeat.setBooking(seat.getBooking());

        Seat updatedSeat = seatRepository.save(existingSeat);
        log.info("Seat updated successfully with ID: {}", updatedSeat.getId());

        return updatedSeat;
    }
}

