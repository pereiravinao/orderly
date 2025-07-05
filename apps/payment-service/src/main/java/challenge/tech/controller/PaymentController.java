package challenge.tech.controller;

import challenge.tech.dto.PaymentRequest;
import challenge.tech.dto.PaymentResponse;
import challenge.tech.usecase.ProcessPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        var payment = processPaymentUseCase.execute(request.toDomain());
        return new ResponseEntity<>(new PaymentResponse(payment), HttpStatus.OK);
    }
}