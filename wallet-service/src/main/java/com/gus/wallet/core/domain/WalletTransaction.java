package com.gus.wallet.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class WalletTransaction {
    private final UUID id;
    private final UUID walletId;
    private final UUID paymentId;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    public WalletTransaction(UUID walletId, UUID paymentId, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.walletId = walletId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public UUID getWalletId() { return walletId; }
    public UUID getPaymentId() { return paymentId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}