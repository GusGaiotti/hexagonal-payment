package com.gus.payment.infrastructure.adapters.output.persistance.repositories;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.infrastructure.adapters.output.persistance.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
