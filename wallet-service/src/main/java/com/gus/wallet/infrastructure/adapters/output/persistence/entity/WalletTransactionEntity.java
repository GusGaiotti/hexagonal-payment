package com.gus.wallet.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable; // ✅ NOVO IMPORT

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallet_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;

    @Column(name = "payment_id", nullable = false, unique = true)
    private UUID paymentId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean isNew() {
        return true;
    }
}