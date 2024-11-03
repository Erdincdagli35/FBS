package com.iyzico.challenge.service.model;

import com.iyzico.challenge.entity.Booking;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.entity.SeatStatus;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SeatModelTest {

        private Seat seat;

        @BeforeEach
        void setUp() {
            seat = new Seat();
            seat.setSeatNumber("12A");
            seat.setStatus(SeatStatus.AVAILABLE);
        }

        @Test
        void testSeatNumber() {
            assertEquals("12A", seat.getSeatNumber());
        }

        @Test
        void testStatus() {
            assertEquals(SeatStatus.AVAILABLE, seat.getStatus());
        }

        @Test
        void testFlight() {
            Flight flight = new Flight();
            flight.setFlightNumber("TK1234");
            seat.setFlight(flight);
            assertEquals(flight, seat.getFlight());
        }

        @Test
        void testBooking() {
            Booking booking = new Booking();
            seat.setBooking(booking);
            assertEquals(booking, seat.getBooking());
        }
    }