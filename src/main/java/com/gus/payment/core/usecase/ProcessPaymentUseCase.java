package com.gus.payment.core.usecase;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.core.ports.PaymentRepositoryPort;

import java.math.BigDecimal;
import java.util.UUID;

public class ProcessPaymentUseCase {

    private final PaymentRepositoryPort paymentRepository;

    public ProcessPaymentUseCase(PaymentRepositoryPort paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public Payment execute(UUID userId, UUID orderId, BigDecimal amount){

        Payment payment = new Payment(userId, orderId, amount);
        return paymentRepository.save(payment);
    }
}
