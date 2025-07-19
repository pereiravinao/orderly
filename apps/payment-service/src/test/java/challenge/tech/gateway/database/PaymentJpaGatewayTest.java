
package challenge.tech.gateway.database;

import challenge.tech.domain.Payment;
import challenge.tech.gateway.database.jpa.entity.PaymentEntity;
import challenge.tech.gateway.database.jpa.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentJpaGatewayTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentJpaGateway paymentJpaGateway;

    @Test
    void shouldSavePayment() {
        Payment payment = new Payment();
        PaymentEntity paymentEntity = new PaymentEntity(payment);

        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

        Payment result = paymentJpaGateway.save(payment);

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenSavingPaymentFails() {
        Payment payment = new Payment();

        when(paymentRepository.save(any(PaymentEntity.class))).thenThrow(new RuntimeException("Database save error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentJpaGateway.save(payment));
        assertEquals("Database save error", exception.getMessage());
    }

    @Test
    void shouldFindByTransactionId() {
        String transactionId = "12345";
        PaymentEntity paymentEntity = new PaymentEntity(new Payment());

        when(paymentRepository.findByTransactionId(transactionId)).thenReturn(Optional.of(paymentEntity));

        Payment result = paymentJpaGateway.findByTransactionId(transactionId);

        assertNotNull(result);
    }
}
