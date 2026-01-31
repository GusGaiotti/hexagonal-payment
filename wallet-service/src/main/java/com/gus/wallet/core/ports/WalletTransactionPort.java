package com.gus.wallet.core.ports;

import com.gus.wallet.core.domain.WalletTransaction;
import java.util.UUID;

public interface WalletTransactionPort {
    boolean existsByPaymentId(UUID paymentId);
    void save(WalletTransaction transaction);
}