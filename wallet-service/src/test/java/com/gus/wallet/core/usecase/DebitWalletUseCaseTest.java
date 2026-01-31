package com.gus.wallet.core.usecase;

import com.gus.wallet.core.domain.Wallet;
import com.gus.wallet.core.ports.WalletRepositoryPort;
import com.gus.wallet.core.ports.WalletTransactionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DebitWalletUseCaseTest {

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    @Mock
    private WalletTransactionPort walletTransactionPort;

    @InjectMocks
    private DebitWalletUseCase debitWalletUseCase;

    @Test
    void shouldDebitWalletSuccessfully() {
        UUID userId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        Wallet wallet = new Wallet(userId);
        wallet.credit(new BigDecimal("100.00"));

        when(walletRepositoryPort.findByUserId(userId)).thenReturn(Optional.of(wallet));
        when(walletTransactionPort.existsByPaymentId(paymentId)).thenReturn(false);

        debitWalletUseCase.execute(userId, paymentId, new BigDecimal("50.00"));

        assertThat(wallet.getBalance()).isEqualByComparingTo(new BigDecimal("50.00"));

        verify(walletRepositoryPort, times(1)).save(wallet);
        verify(walletTransactionPort, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        UUID userId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        Wallet wallet = new Wallet(userId);

        when(walletRepositoryPort.findByUserId(userId)).thenReturn(Optional.of(wallet));
        when(walletTransactionPort.existsByPaymentId(paymentId)).thenReturn(false);

        assertThatThrownBy(() -> debitWalletUseCase.execute(userId, paymentId, new BigDecimal("50.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wallet has no balance.");

        verify(walletRepositoryPort, never()).save(any());
        verify(walletTransactionPort, never()).save(any());
    }
}