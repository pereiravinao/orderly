package challenge.tech.gateway.payment.impl;

import challenge.tech.domain.PaymentStatus;
import challenge.tech.dto.WebhookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class WebhookSimulator {

    private final RestTemplate restTemplate;

    @Async
    public void simulateWebhook(String transactionId, Long orderId) {
        try {
            Thread.sleep(1000);

            WebhookRequest webhookRequest = new WebhookRequest();
            webhookRequest.setTransactionId(transactionId);
            webhookRequest.setOrderId(orderId);
            webhookRequest.setStatus(new Random().nextBoolean() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

            restTemplate.postForEntity("http://localhost:8084/api/v1/orders/webhook", webhookRequest, Void.class);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}