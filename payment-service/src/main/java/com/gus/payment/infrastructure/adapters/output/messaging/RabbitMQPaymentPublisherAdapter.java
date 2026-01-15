package com.gus.payment.infrastructure.adapters.output.messaging;

import com.gus.payment.core.events.PaymentCreatedEvent;
import com.gus.payment.core.ports.PaymentEventPublisherPort;
import com.gus.payment.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQPaymentPublisherAdapter implements PaymentEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Override
    public void publish(PaymentCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                event
        );
    }
}