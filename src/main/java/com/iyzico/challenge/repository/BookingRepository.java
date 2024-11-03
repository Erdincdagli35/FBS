package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findOneById(Long bookingId);
}
