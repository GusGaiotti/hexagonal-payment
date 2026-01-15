package com.gus.wallet.infrastructure.adapters.output.persistence;

import com.gus.wallet.core.domain.Wallet;
import com.gus.wallet.core.ports.WalletRepositoryPort;
import com.gus.wallet.infrastructure.adapters.output.persistence.entity.WalletEntity;
import com.gus.wallet.infrastructure.adapters.output.persistence.mapper.WalletPersistenceMapper;
import com.gus.wallet.infrastructure.adapters.output.persistence.repository.SpringWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletRepositoryPort {

    private final SpringWalletRepository springWalletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity entity = walletPersistenceMapper.toEntity(wallet);
        WalletEntity savedEntity = springWalletRepository.save(entity);
        return walletPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Wallet> findByUserId(UUID userId) {
        return springWalletRepository.findByUserId(userId)
                .map(walletPersistenceMapper::toDomain);
    }
}