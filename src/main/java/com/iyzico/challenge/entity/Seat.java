package com.iyzico.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue
    private Long id;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status = SeatStatus.AVAILABLE; // available, booked, reserved


    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL)
    private Booking booking;
}

