package com.gus.wallet.core.usecase;

import com.gus.wallet.core.domain.Wallet;
import com.gus.wallet.core.domain.WalletTransaction;
import com.gus.wallet.core.ports.WalletRepositoryPort;
import com.gus.wallet.core.ports.WalletTransactionPort; // Nova porta
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class DebitWalletUseCase {

    private static final Logger log = LoggerFactory.getLogger(DebitWalletUseCase.class);

    private final WalletRepositoryPort walletRepositoryPort;
    private final WalletTransactionPort walletTransactionPort; // Injeção nova

    public DebitWalletUseCase(WalletRepositoryPort walletRepositoryPort,
                              WalletTransactionPort walletTransactionPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.walletTransactionPort = walletTransactionPort;
    }

    public void execute(UUID userId, UUID paymentId, BigDecimal amount) {

        if (walletTransactionPort.existsByPaymentId(paymentId)) {
            log.warn("Pagamento duplicado ignorado: {}", paymentId);
            return;
        }

        Wallet wallet = walletRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));

        wallet.debit(amount);

        walletRepositoryPort.save(wallet);

        WalletTransaction transaction = new WalletTransaction(wallet.getId(), paymentId, amount);
        walletTransactionPort.save(transaction);

        log.info("Débito realizado com sucesso! Wallet: {}, Novo saldo: {}", wallet.getId(), wallet.getBalance());
    }
}