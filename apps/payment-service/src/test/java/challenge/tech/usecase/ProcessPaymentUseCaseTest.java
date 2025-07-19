
package challenge.tech.usecase;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import challenge.tech.gateway.database.PaymentJpaGateway;
import challenge.tech.gateway.payment.PaymentGateway;
import challenge.tech.gateway.payment.impl.WebhookSimulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ProcessPaymentUseCaseTest {

    @Mock
    private PaymentJpaGateway paymentJpaGateway;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private WebhookSimulator webhookSimulator;

    @InjectMocks
    private ProcessPaymentUseCase processPaymentUseCase;

    @Test
    void shouldProcessPaymentSuccessfully() {
        Payment request = new Payment();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        Payment paymentProcessed = new Payment();
        paymentProcessed.setTransactionId("12345");
        paymentProcessed.setStatus(PaymentStatus.SUCCESS);

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(paymentProcessed);
        when(paymentJpaGateway.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = processPaymentUseCase.execute(request);

        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        assertEquals("12345", result.getTransactionId());
    }

    @Test
    void shouldHandleFailedPaymentProcessing() {
        Payment request = new Payment();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        Payment paymentProcessed = new Payment();
        paymentProcessed.setTransactionId("failed-id");
        paymentProcessed.setStatus(PaymentStatus.FAILED);

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(paymentProcessed);
        when(paymentJpaGateway.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = processPaymentUseCase.execute(request);

        assertEquals(PaymentStatus.FAILED, result.getStatus());
        assertEquals("failed-id", result.getTransactionId());
    }

    @Test
    void shouldHandleExceptionWhenSavingPayment() {
        Payment request = new Payment();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        Payment paymentProcessed = new Payment();
        paymentProcessed.setTransactionId("12345");
        paymentProcessed.setStatus(PaymentStatus.SUCCESS);

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(paymentProcessed);
        when(paymentJpaGateway.save(any(Payment.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> processPaymentUseCase.execute(request));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void shouldHandleExceptionWhenSimulatingWebhook() {
        Payment request = new Payment();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        Payment paymentProcessed = new Payment();
        paymentProcessed.setTransactionId("12345");
        paymentProcessed.setStatus(PaymentStatus.SUCCESS);

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(paymentProcessed);
        when(paymentJpaGateway.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doThrow(new RuntimeException("Webhook simulation failed")).when(webhookSimulator).simulateWebhook(anyString(), any());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> processPaymentUseCase.execute(request));
        assertEquals("Webhook simulation failed", exception.getMessage());
    }
}
