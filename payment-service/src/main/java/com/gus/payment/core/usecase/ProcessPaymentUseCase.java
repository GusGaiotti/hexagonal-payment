package com.gus.payment.core.usecase;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.core.events.PaymentCreatedEvent;
import com.gus.payment.core.ports.PaymentEventPublisherPort;
import com.gus.payment.core.ports.PaymentRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class ProcessPaymentUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

    private final PaymentRepositoryPort paymentRepository;
    private final PaymentEventPublisherPort paymentEventPublisher;

    public ProcessPaymentUseCase(PaymentRepositoryPort paymentRepository,
                                 PaymentEventPublisherPort paymentEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    public Payment execute(UUID userId, UUID orderId, BigDecimal amount) {

        Optional<Payment> existingPayment = paymentRepository.findByOrderId(orderId);
        if (existingPayment.isPresent()) {
            log.info("Payment já existe para orderId: {}. Retornando payment existente.", orderId);
            return existingPayment.get();
        }

        Payment payment = new Payment(userId, orderId, amount);
        Payment savedPayment = paymentRepository.save(payment);

        PaymentCreatedEvent event = new PaymentCreatedEvent(
                savedPayment.getId(),
                savedPayment.getUserId(),
                savedPayment.getOrderId(),
                savedPayment.getAmount(),
                savedPayment.getStatus().name()
        );

        paymentEventPublisher.publish(event);

        log.info("Payment criado com sucesso: {}", savedPayment.getId());
        return savedPayment;
    }
}