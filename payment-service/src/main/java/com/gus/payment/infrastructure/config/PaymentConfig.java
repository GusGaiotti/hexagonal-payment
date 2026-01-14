package com.gus.payment.infrastructure.config;

import com.gus.payment.core.ports.PaymentEventPublisherPort;
import com.gus.payment.core.ports.PaymentRepositoryPort;
import com.gus.payment.core.usecase.ProcessPaymentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(
            PaymentRepositoryPort paymentRepositoryPort,
            PaymentEventPublisherPort paymentEventPublisherPort
    ) {
        return new ProcessPaymentUseCase(paymentRepositoryPort, paymentEventPublisherPort);
    }
}