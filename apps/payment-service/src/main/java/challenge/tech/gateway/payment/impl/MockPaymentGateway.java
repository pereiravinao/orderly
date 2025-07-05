package challenge.tech.gateway.payment.impl;

import challenge.tech.domain.Payment;
import challenge.tech.gateway.payment.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public Payment processPayment(Payment request) {
        var transactionId = UUID.randomUUID().toString();
        var status = this.buildStatus();

        request.setTransactionId(transactionId);
        request.setStatus(status);

        return request;
    }

    private String buildStatus() {
        boolean success = new Random().nextBoolean();
        return success ? "SUCCESS" : "FAILED";
    }
}