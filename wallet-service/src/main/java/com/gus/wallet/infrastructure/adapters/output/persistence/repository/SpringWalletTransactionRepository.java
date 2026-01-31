package com.gus.wallet.infrastructure.adapters.output.persistence.repository;

import com.gus.wallet.infrastructure.adapters.output.persistence.entity.WalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringWalletTransactionRepository extends JpaRepository<WalletTransactionEntity, UUID> {
    boolean existsByPaymentId(UUID paymentId);
}