
package challenge.tech.gateway.payment.impl;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MockPaymentGatewayTest {

    @InjectMocks
    private MockPaymentGateway mockPaymentGateway;

    @Test
    void shouldProcessPayment() {
        Payment request = new Payment();

        Payment result = mockPaymentGateway.processPayment(request);

        assertNotNull(result.getTransactionId());
        assertEquals(PaymentStatus.PENDING, result.getStatus());
    }
}
