package com.iyzico.challenge.service.model;

import static org.junit.jupiter.api.Assertions.*;

import com.iyzico.challenge.entity.Booking;
import com.iyzico.challenge.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class PaymentModelTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setPrice(new BigDecimal("250.00"));
        payment.setBankResponse("Success");
    }

    @Test
    void testPrice() {
        assertEquals(new BigDecimal("250.00"), payment.getPrice());
    }

    @Test
    void testBankResponse() {
        assertEquals("Success", payment.getBankResponse());
    }
}