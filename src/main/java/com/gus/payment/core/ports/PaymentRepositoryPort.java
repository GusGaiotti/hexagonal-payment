package com.gus.payment.core.ports;

import com.gus.payment.core.domain.Payment;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
}
