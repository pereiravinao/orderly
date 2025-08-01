package challenge.tech.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Payment {
    private Long id;
    private Long orderId;
    private String transactionId;
    private BigDecimal amount;
    private String cardNumber;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}