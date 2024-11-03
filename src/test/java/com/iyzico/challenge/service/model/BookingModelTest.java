package com.iyzico.challenge.service.model;

import static org.junit.jupiter.api.Assertions.*;

import com.iyzico.challenge.entity.Booking;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.entity.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingModelTest {

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setDescription("Booking for flight TK1234");
    }

    @Test
    void testDescription() {
        assertEquals("Booking for flight TK1234", booking.getDescription());
    }

    @Test
    void testSeat() {
        Seat seat = new Seat();
        seat.setSeatNumber("12A");
        booking.setSeat(seat);
        assertEquals(seat, booking.getSeat());
    }

    @Test
    void testFlight() {
        Flight flight = new Flight();
        flight.setFlightNumber("TK1234");
        booking.setFlight(flight);
        assertEquals(flight, booking.getFlight());
    }
}