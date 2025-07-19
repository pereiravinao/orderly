package challenge.tech.controller;

import challenge.tech.dto.WebhookRequest;
import challenge.tech.usecase.UpdatePaymentStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/payments/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @PostMapping
    public ResponseEntity<Void> updatePaymentStatus(@Valid @RequestBody WebhookRequest request) {
        updatePaymentStatusUseCase.execute(request.getTransactionId(), request.getStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}