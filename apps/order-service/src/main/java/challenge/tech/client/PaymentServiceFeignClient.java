package challenge.tech.client;

import challenge.tech.client.dto.PaymentRequest;
import challenge.tech.client.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${clients.payment-service.url}")
public interface PaymentServiceFeignClient {

    @PostMapping("/v1/payments")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}