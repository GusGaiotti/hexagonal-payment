package com.gus.wallet.infrastructure.adapters.input.event.listener;

import com.gus.wallet.core.usecase.DebitWalletUseCase;
import com.gus.wallet.infrastructure.adapters.input.event.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletPaymentListener {

    private final DebitWalletUseCase debitWalletUseCase;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receivePaymentCreatedEvent(PaymentEvent event) {

        log.info("Evento recebido: {}", event);

        try {
            debitWalletUseCase.execute(event.userId(), event.amount());

            log.info("Débito processado com sucesso para User: {}", event.userId());

        } catch (Exception e) {
            log.error("Falha ao processar débito: {}", e.getMessage());
        }
    }
}