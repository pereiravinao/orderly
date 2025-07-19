package challenge.tech.usecase;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import challenge.tech.gateway.database.PaymentJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePaymentStatusUseCase {

    private final PaymentJpaGateway paymentJpaGateway;

    public void execute(String transactionId, PaymentStatus status) {
        Payment payment = paymentJpaGateway.findByTransactionId(transactionId);
        if (payment == null) {
            throw new RuntimeException("Payment not found for transactionId: " + transactionId);
        }
        payment.setStatus(status);
        paymentJpaGateway.save(payment);

    }
}