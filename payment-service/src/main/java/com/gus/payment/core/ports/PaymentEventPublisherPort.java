package com.gus.payment.core.ports;

import com.gus.payment.core.events.PaymentCreatedEvent;

public interface PaymentEventPublisherPort {
    void publish(PaymentCreatedEvent event);
}