package com.gus.payment.core.ports;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentValidatorPort {
    void validate(UUID paymentId, BigDecimal amount);
}