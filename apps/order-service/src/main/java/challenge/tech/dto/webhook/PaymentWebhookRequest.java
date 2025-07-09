package challenge.tech.dto.webhook;

import challenge.tech.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentWebhookRequest {
    private Long orderId;
    private String transactionId;
    private OrderStatus status;
}