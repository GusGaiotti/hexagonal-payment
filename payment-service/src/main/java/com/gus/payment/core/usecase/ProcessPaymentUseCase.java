package com.gus.payment.core.usecase;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.core.events.PaymentCreatedEvent;
import com.gus.payment.core.ports.PaymentEventPublisherPort;
import com.gus.payment.core.ports.PaymentRepositoryPort;
import com.gus.payment.core.ports.PaymentValidatorPort; // Import Novo
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class ProcessPaymentUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

    private final PaymentRepositoryPort paymentRepository;
    private final PaymentEventPublisherPort paymentEventPublisher;
    private final PaymentValidatorPort paymentValidator;

    public ProcessPaymentUseCase(PaymentRepositoryPort paymentRepository,
                                 PaymentEventPublisherPort paymentEventPublisher,
                                 PaymentValidatorPort paymentValidator) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
        this.paymentValidator = paymentValidator;
    }

    public Payment execute(UUID userId, UUID orderId, BigDecimal amount) {

        Optional<Payment> existingPayment = paymentRepository.findByOrderId(orderId);
        if (existingPayment.isPresent()) {
            log.info("Payment já existe para orderId: {}. Retornando payment existente.", orderId);
            return existingPayment.get();
        }

        Payment payment = new Payment(userId, orderId, amount);
        payment = paymentRepository.save(payment);
        log.info("Pagamento iniciado (PENDING). ID: {}", payment.getId());

        try {
            paymentValidator.validate(payment.getId(), payment.getAmount());

            payment.authorize();
            paymentRepository.save(payment);

            PaymentCreatedEvent event = new PaymentCreatedEvent(
                    payment.getId(),
                    payment.getUserId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    payment.getStatus().name()
            );
            paymentEventPublisher.publish(event);

            log.info("Pagamento autorizado e enviado para liquidação. ID: {}", payment.getId());

        } catch (Exception e) {
            log.warn("Pagamento recusado pelo Antifraude. Motivo: {}", e.getMessage());

            payment.reject();
            paymentRepository.save(payment);
        }

        return payment;
    }
}