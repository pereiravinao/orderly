
package challenge.tech.usecase;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import challenge.tech.gateway.database.PaymentJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentStatusUseCaseTest {

    @Mock
    private PaymentJpaGateway paymentJpaGateway;

    @InjectMocks
    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @Test
    void shouldUpdatePaymentStatus() {
        String transactionId = "12345";
        PaymentStatus status = PaymentStatus.SUCCESS;

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);

        when(paymentJpaGateway.findByTransactionId(transactionId)).thenReturn(payment);

        updatePaymentStatusUseCase.execute(transactionId, status);

        verify(paymentJpaGateway).save(payment);
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {
        String transactionId = "non-existent-id";
        PaymentStatus status = PaymentStatus.FAILED;

        when(paymentJpaGateway.findByTransactionId(transactionId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updatePaymentStatusUseCase.execute(transactionId, status));
        assertEquals("Payment not found for transactionId: " + transactionId, exception.getMessage());
    }

    @Test
    void shouldHandleExceptionWhenSavingPayment() {
        String transactionId = "12345";
        PaymentStatus status = PaymentStatus.SUCCESS;

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);

        when(paymentJpaGateway.findByTransactionId(transactionId)).thenReturn(payment);
        doThrow(new RuntimeException("Database error")).when(paymentJpaGateway).save(any(Payment.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updatePaymentStatusUseCase.execute(transactionId, status));
        assertEquals("Database error", exception.getMessage());
    }
}
