package com.gus.payment.infrastructure.adapters.output.messaging;

import com.gus.payment.core.events.PaymentCreatedEvent;
import com.gus.payment.core.ports.PaymentEventPublisherPort;
import com.gus.payment.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQPaymentPublisherAdapter implements PaymentEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(PaymentCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAYMENT_EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}