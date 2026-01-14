package com.gus.payment.core.events;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreatedEvent(
        UUID paymentId,
        UUID userId,
        UUID orderId,
        BigDecimal amount,
        String status
) {
}