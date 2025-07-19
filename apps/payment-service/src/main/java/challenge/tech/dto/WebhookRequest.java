package challenge.tech.dto;

import challenge.tech.domain.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class WebhookRequest {
    @NotNull
    private String transactionId;
    private Long orderId;
    @NotNull
    private PaymentStatus status;
}