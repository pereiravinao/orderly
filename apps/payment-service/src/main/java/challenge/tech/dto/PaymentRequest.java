package challenge.tech.dto;

import challenge.tech.domain.Payment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private String cardNumber;

    public Payment toDomain() {
        var payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setCardNumber(cardNumber);
        return payment;
    }
}