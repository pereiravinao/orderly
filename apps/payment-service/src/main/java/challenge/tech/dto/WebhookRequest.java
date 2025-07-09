package challenge.tech.dto;

import challenge.tech.domain.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookRequest {
    private String transactionId;
    private Long orderId;
    private PaymentStatus status;
}