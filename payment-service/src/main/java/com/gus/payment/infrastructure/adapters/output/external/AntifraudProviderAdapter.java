package com.gus.payment.infrastructure.adapters.output.external;

import com.gus.payment.core.ports.PaymentValidatorPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AntifraudProviderAdapter implements PaymentValidatorPort {

    private final RestClient restClient = RestClient.create();

    private static final String ANTIFRAUD_URL = "https://httpbin.org/status/200";

    @Override
    public void validate(UUID paymentId, BigDecimal amount) {
        log.info("Iniciando análise de risco no Antifraude... ID: {}", paymentId);

        try {
            var response = restClient.get()
                    .uri(ANTIFRAUD_URL)
                    .retrieve()
                    .toBodilessEntity();

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Antifraude aprovou a transação! ID: {}", paymentId);
            } else {
                throw new RuntimeException("Antifraude retornou status não-200");
            }

        } catch (Exception e) {
            log.error("Erro na validação de fraude: {}", e.getMessage());
            throw new RuntimeException("Transação recusada pelo sistema de risco.");
        }
    }
}