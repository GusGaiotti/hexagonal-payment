package com.gus.payment.infrastructure.adapters.output.persistance.mapper;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.infrastructure.adapters.output.persistance.entities.PaymentEntity;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class PaymentPersistenceMapper {

    public PaymentEntity toEntity(Payment domain){

        return new PaymentEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getOrderId(),
                domain.getAmount(),
                domain.getStatus().name(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    public Payment toDomain(PaymentEntity paymentEntity){

        return new Payment(paymentEntity.getUserId(), paymentEntity.getOrderId(), paymentEntity.getAmount());
    }

}
