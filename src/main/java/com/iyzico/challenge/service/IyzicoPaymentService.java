package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.*;
import com.iyzico.challenge.repository.BookingRepository;
import com.iyzico.challenge.repository.FlightRepository;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzico.challenge.repository.SeatRepository;
import com.iyzico.challenge.request.BankPaymentRequest;
import com.iyzico.challenge.response.BankPaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    private BankService bankService;
    private PaymentRepository paymentRepository;

    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String PAYMENT_TOPIC = "payments";

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository,
                                KafkaTemplate<String, String> kafkaTemplate) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void pay(BigDecimal price) {
        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);

        //insert records
        Payment payment = new Payment();
        payment.setBankResponse(response.getResultCode());
        payment.setPrice(price);

        paymentRepository.save(payment);

        kafkaTemplate.send(PAYMENT_TOPIC,payment.toString());
    }
}
