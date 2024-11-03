package com.iyzico.challenge.configuration;

import com.iyzico.challenge.service.IyzicoPaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
public class PaymentRequestConsumer {

    private final IyzicoPaymentService iyzicoPaymentService;

    public PaymentRequestConsumer(IyzicoPaymentService iyzicoPaymentService) {
        this.iyzicoPaymentService = iyzicoPaymentService;
    }

    @KafkaListener(topics = "payment-requests", groupId = "payment-group")
    public void consume(String message) {
        log.info("Consumed message: " + message);

        BigDecimal price = new BigDecimal(message);
        iyzicoPaymentService.pay(price);
    }
}
