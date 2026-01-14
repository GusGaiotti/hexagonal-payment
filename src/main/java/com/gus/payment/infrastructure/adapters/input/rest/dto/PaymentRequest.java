package com.gus.payment.infrastructure.adapters.input.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(UUID userId, UUID orderId, BigDecimal amount) {
}
