package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentEntityTest {

    @Test
    void testPaymentEntityConstructorFromPayment() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(100L);
        payment.setTransactionId("trans123");
        payment.setAmount(BigDecimal.valueOf(50.0));
        payment.setCardNumber("1234-5678-9012-3456");
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setCreatedAt(LocalDateTime.now());

        PaymentEntity entity = new PaymentEntity(payment);

        assertEquals(payment.getId(), entity.getId());
        assertEquals(payment.getOrderId(), entity.getOrderId());
        assertEquals(payment.getTransactionId(), entity.getTransactionId());
        assertEquals(payment.getAmount(), entity.getAmount());
        assertEquals(payment.getCardNumber(), entity.getCardNumber());
        assertEquals(payment.getStatus(), entity.getStatus());
        assertEquals(payment.getCreatedAt(), entity.getCreatedAt());
    }

    @Test
    void testToDomain() {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(1L);
        entity.setOrderId(100L);
        entity.setTransactionId("trans123");
        entity.setAmount(BigDecimal.valueOf(50.0));
        entity.setCardNumber("1234-5678-9012-3456");
        entity.setStatus(PaymentStatus.SUCCESS);
        entity.setCreatedAt(LocalDateTime.now());

        Payment payment = entity.toDomain();

        assertEquals(entity.getId(), payment.getId());
        assertEquals(entity.getOrderId(), payment.getOrderId());
        assertEquals(entity.getTransactionId(), payment.getTransactionId());
        assertEquals(entity.getAmount(), payment.getAmount());
        assertEquals(entity.getCardNumber(), payment.getCardNumber());
        assertEquals(entity.getStatus(), payment.getStatus());
        assertEquals(entity.getCreatedAt(), payment.getCreatedAt());
    }

    @Test
    void testPrePersistSetsCreatedAtAndStatusWhenNull() {
        PaymentEntity entity = new PaymentEntity();
        assertNull(entity.getCreatedAt());
        assertNull(entity.getStatus());

        entity.prePersist();

        assertNotNull(entity.getCreatedAt());
        assertEquals(PaymentStatus.PENDING, entity.getStatus());
    }

    @Test
    void testPrePersistDoesNotChangeExistingCreatedAtAndStatus() {
        PaymentEntity entity = new PaymentEntity();
        LocalDateTime existingCreatedAt = LocalDateTime.of(2023, 1, 1, 10, 0);
        PaymentStatus existingStatus = PaymentStatus.SUCCESS;

        entity.setCreatedAt(existingCreatedAt);
        entity.setStatus(existingStatus);

        entity.prePersist();

        assertEquals(existingCreatedAt, entity.getCreatedAt());
        assertEquals(existingStatus, entity.getStatus());
    }
}