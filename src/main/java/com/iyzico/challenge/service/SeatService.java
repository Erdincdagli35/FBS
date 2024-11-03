package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.response.FlightSeatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {
    List<String> addSeat(List<String> seatNumbers, Long flightId);

    List<FlightSeatResponse> getAllSeats();

    Seat getSeatById(Long seatId);

    Long deleteSeat(Long seatId);

    Seat updateSeat(Seat seat);
}
