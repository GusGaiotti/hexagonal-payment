package com.gus.payment.infrastructure.adapters.input.rest;

import com.gus.payment.core.domain.Payment;
import com.gus.payment.core.usecase.ProcessPaymentUseCase;
import com.gus.payment.infrastructure.adapters.input.rest.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest request) {

        Payment payment = processPaymentUseCase.execute(
                request.userId(),
                request.orderId(),
                request.amount()
        );

        return ResponseEntity.ok(payment);
    }
}