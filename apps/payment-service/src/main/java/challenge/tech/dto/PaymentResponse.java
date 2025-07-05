package challenge.tech.dto;

import challenge.tech.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long id;
    private String transactionId;
    private String status;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.transactionId = payment.getTransactionId();
        this.status = payment.getStatus();

    }
}