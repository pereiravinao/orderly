
package challenge.tech.dto;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoTest {

    @Test
    void testPaymentRequest() {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        Payment payment = request.toDomain();

        assertEquals(1L, payment.getOrderId());
        assertEquals(BigDecimal.valueOf(100.0), payment.getAmount());
    }

    @Test
    void testPaymentResponse() {
        Payment payment = new Payment();
        payment.setTransactionId("12345");
        payment.setStatus(PaymentStatus.SUCCESS);

        PaymentResponse response = new PaymentResponse(payment);

        assertEquals("12345", response.getTransactionId());
        assertEquals(PaymentStatus.SUCCESS.name(), response.getStatus());
    }

    @Test
    void testWebhookRequest() {
        WebhookRequest request = new WebhookRequest();
        request.setTransactionId("12345");
        request.setOrderId(1L);
        request.setStatus(PaymentStatus.SUCCESS);

        assertEquals("12345", request.getTransactionId());
        assertEquals(1L, request.getOrderId());
        assertEquals(PaymentStatus.SUCCESS, request.getStatus());
    }
}
