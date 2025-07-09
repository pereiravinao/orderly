package challenge.tech.gateway.database;

import challenge.tech.domain.Payment;
import challenge.tech.gateway.database.jpa.entity.PaymentEntity;
import challenge.tech.gateway.database.jpa.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentJpaGateway {

    private final PaymentRepository paymentRepository;

    public Payment save(Payment paymentParameter) {
        return paymentRepository.save(new PaymentEntity(paymentParameter)).toDomain();
    }

    public Payment findByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId).map(PaymentEntity::toDomain).orElse(null);
    }
}
