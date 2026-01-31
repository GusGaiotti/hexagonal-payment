package com.gus.payment.core.ports;

import com.gus.payment.core.domain.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
