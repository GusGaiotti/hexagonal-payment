package com.gus.wallet.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {

    private final UUID id;
    private final UUID userId;
    private BigDecimal balance;

    public Wallet(UUID userId){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.balance = BigDecimal.ZERO;
    }

    public Wallet(UUID id, UUID userId, BigDecimal balance){
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public void debit(BigDecimal amount){
        if (this.balance.compareTo(amount) < 0){
            throw new IllegalArgumentException("Wallet has no balance.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount has to be positive.");
        }
        this.balance = this.balance.add(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getId() {
        return id;
    }
}
