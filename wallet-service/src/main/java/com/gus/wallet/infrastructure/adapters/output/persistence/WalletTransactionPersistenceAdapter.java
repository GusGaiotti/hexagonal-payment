package com.gus.wallet.infrastructure.adapters.output.persistence;

import com.gus.wallet.core.domain.WalletTransaction;
import com.gus.wallet.core.ports.WalletTransactionPort;
import com.gus.wallet.infrastructure.adapters.output.persistence.mapper.WalletPersistenceMapper;
import com.gus.wallet.infrastructure.adapters.output.persistence.repository.SpringWalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletTransactionPersistenceAdapter implements WalletTransactionPort {

    private final SpringWalletTransactionRepository repository;
    private final WalletPersistenceMapper mapper;

    @Override
    public boolean existsByPaymentId(UUID paymentId) {
        return repository.existsByPaymentId(paymentId);
    }

    @Override
    public void save(WalletTransaction transaction) {
        repository.save(mapper.toTransactionEntity(transaction));
    }
}