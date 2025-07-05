package challenge.tech.gateway.payment;

import challenge.tech.domain.Payment;

public interface PaymentGateway {
    Payment processPayment(Payment request);
}