package com.gus.wallet.infrastructure.adapters.input.event;

import com.gus.wallet.infrastructure.adapters.input.event.dto.PaymentEvent;
import com.gus.wallet.infrastructure.adapters.output.persistence.entity.WalletEntity;
import com.gus.wallet.infrastructure.adapters.output.persistence.repository.SpringWalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class WalletPaymentIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    @ServiceConnection
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:3.12-management-alpine");

    @Autowired
    private SpringWalletRepository walletRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Test
    void shouldDebitWalletWhenReceivingPaymentEvent() {
        UUID userId = UUID.randomUUID();
        WalletEntity wallet = WalletEntity.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .balance(new BigDecimal("100.00"))
                .build();
        walletRepository.save(wallet);

        PaymentEvent event = new PaymentEvent(
                UUID.randomUUID(),
                userId,
                UUID.randomUUID(),
                new BigDecimal("10.00"),
                "APPROVED"
        );
        rabbitTemplate.convertAndSend(queueName, event);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            WalletEntity updatedWallet = walletRepository.findByUserId(userId).orElseThrow();

            assertThat(updatedWallet.getBalance()).isEqualByComparingTo(new BigDecimal("90.00"));
        });
    }
}