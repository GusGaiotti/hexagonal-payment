package com.gus.wallet.infrastructure.adapters.output.persistence.mapper;

import com.gus.wallet.core.domain.Wallet;
import com.gus.wallet.infrastructure.adapters.output.persistence.entity.WalletEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletPersistenceMapper {

    public WalletEntity toEntity(Wallet domain) {
        if (domain == null) return null;

        return new WalletEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getBalance()
        );
    }

    public Wallet toDomain(WalletEntity entity) {
        if (entity == null) return null;

        return new Wallet(
                entity.getId(),
                entity.getUserId(),
                entity.getBalance()
        );
    }
}