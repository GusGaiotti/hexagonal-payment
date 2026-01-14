package com.gus.payment.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

    private UUID id;
    private UUID userId;
    private UUID orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Payment(UUID userId, UUID orderId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        this.id = UUID.randomUUID();
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public void approve() {

        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment cannot be approved in current status: " + this.status);
        }
        this.status = PaymentStatus.APPROVED;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(){

        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is already: " + this.status);
        }

        this.status = PaymentStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getId() {
        return id;
    }

}
