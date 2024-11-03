package com.iyzico.challenge.response;

import com.iyzico.challenge.entity.SeatStatus;
import lombok.Data;

@Data
public class FlightSeatResponse {
    private String flightName;
    private String description;
    private String seatNumber;
    private SeatStatus seatStatus;
    private Double price;
}
