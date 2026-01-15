package com.gus.wallet.infrastructure.adapters.input.event.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentEvent(
        UUID id,
        UUID userId,
        UUID orderId,
        BigDecimal amount,
        String status
) {}