package com.iyzico.challenge.service.model;

import static org.junit.jupiter.api.Assertions.*;

import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FlightTest {

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        flight.setFlightNumber("TK1234");
        flight.setOrigin("Istanbul");
        flight.setDestination("Ankara");
        flight.setDepartureTime(LocalDateTime.of(2024, 10, 29, 15, 0));
        flight.setPrice(250.0);
        flight.setSeats(new ArrayList<>());
    }

    @Test
    void testFlightNumber() {
        assertEquals("TK1234", flight.getFlightNumber());
    }

    @Test
    void testOrigin() {
        assertEquals("Istanbul", flight.getOrigin());
    }

    @Test
    void testDestination() {
        assertEquals("Ankara", flight.getDestination());
    }

    @Test
    void testDepartureTime() {
        assertEquals(LocalDateTime.of(2024, 10, 29, 15, 0), flight.getDepartureTime());
    }

    @Test
    void testPrice() {
        assertEquals(250.0, flight.getPrice());
    }

    @Test
    void testSeats() {
        List<Seat> seats = flight.getSeats();
        assertNotNull(seats);
        assertTrue(seats.isEmpty());
    }
}
