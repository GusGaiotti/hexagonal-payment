package com.gus.wallet.infrastructure.config;

import com.gus.wallet.core.ports.WalletRepositoryPort;
import com.gus.wallet.core.ports.WalletTransactionPort;
import com.gus.wallet.core.usecase.DebitWalletUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletConfig {

    @Bean
    public DebitWalletUseCase debitWalletUseCase(WalletRepositoryPort walletRepositoryPort,
                                                 WalletTransactionPort walletTransactionPort) {
        return new DebitWalletUseCase(walletRepositoryPort, walletTransactionPort);
    }
}