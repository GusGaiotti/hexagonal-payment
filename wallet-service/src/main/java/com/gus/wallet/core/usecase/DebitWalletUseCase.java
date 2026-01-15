package com.gus.wallet.core.usecase;

import com.gus.wallet.core.domain.Wallet;
import com.gus.wallet.core.ports.WalletRepositoryPort;

import java.math.BigDecimal;
import java.util.UUID;

public class DebitWalletUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public DebitWalletUseCase(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    public void execute(UUID userId, BigDecimal amount) {
        Wallet wallet = walletRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));

        wallet.debit(amount);

        walletRepositoryPort.save(wallet);

        System.out.println("Débito realizado com sucesso! Novo saldo: " + wallet.getBalance());
    }
}