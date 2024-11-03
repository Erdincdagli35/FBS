package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Seat findOneById(Long id);

    List<Seat> findAllByFlight_IdOrderBySeatNumberAsc(Long flightId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Seat findOneByFlight_IdAndSeatNumber(Long flightId, String seatNumber);
}
