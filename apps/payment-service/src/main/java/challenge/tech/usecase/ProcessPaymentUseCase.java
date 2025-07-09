package challenge.tech.usecase;

import challenge.tech.domain.Payment;
import challenge.tech.gateway.database.PaymentJpaGateway;
import challenge.tech.gateway.payment.PaymentGateway;
import challenge.tech.gateway.payment.impl.WebhookSimulator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessPaymentUseCase {

    private final PaymentJpaGateway paymentJpaGateway;
    private final PaymentGateway paymentGateway;
    private final WebhookSimulator webhookSimulator;

    public Payment execute(Payment request) {

        var paymentProcessed = paymentGateway.processPayment(request);

        request.setTransactionId(paymentProcessed.getTransactionId());
        request.setStatus(paymentProcessed.getStatus());
        var payment = paymentJpaGateway.save(request);

        webhookSimulator.simulateWebhook(payment.getTransactionId(), payment.getOrderId());

        return payment;

    }
}