package com.gus.wallet.core.ports;

import com.gus.wallet.core.domain.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepositoryPort {
    Wallet save(Wallet wallet);
    Optional<Wallet> findByUserId(UUID userId);
}
