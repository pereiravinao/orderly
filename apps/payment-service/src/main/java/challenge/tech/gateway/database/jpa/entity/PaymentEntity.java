package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_payment")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String transactionId;
    private BigDecimal amount;
    private String cardNumber;


    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public PaymentEntity(Payment payment) {
        this.id = payment.getId();
        this.orderId = payment.getOrderId();
        this.transactionId = payment.getTransactionId();
        this.amount = payment.getAmount();
        this.status = payment.getStatus();
        this.cardNumber = payment.getCardNumber();
        this.createdAt = payment.getCreatedAt();
    }

    public Payment toDomain() {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setOrderId(orderId);
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setStatus(status);
        payment.setCardNumber(cardNumber);
        payment.setCreatedAt(createdAt);
        return payment;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }
}