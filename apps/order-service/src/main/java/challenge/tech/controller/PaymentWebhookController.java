package challenge.tech.controller;

import challenge.tech.dto.webhook.PaymentWebhookRequest;
import challenge.tech.usecase.UpdateOrderStatusFromWebhookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final UpdateOrderStatusFromWebhookUseCase updateOrderStatusFromWebhookUseCase;

    @PostMapping
    public ResponseEntity<Void> receivePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        updateOrderStatusFromWebhookUseCase.execute(request.getOrderId(), request.getStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}