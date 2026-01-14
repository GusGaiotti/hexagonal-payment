package com.gus.payment.infrastructure.adapters.output.persistance;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.core.ports.PaymentRepositoryPort;
import com.gus.payment.infrastructure.adapters.output.persistance.entities.PaymentEntity;
import com.gus.payment.infrastructure.adapters.output.persistance.mapper.PaymentPersistenceMapper;
import com.gus.payment.infrastructure.adapters.output.persistance.repositories.SpringPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final SpringPaymentRepository springPaymentRepository;
    private final PaymentPersistenceMapper paymentMapper;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = paymentMapper.toEntity(payment);

        PaymentEntity savedEntity = springPaymentRepository.save(entity);

        return payment;
    }
}