package challenge.tech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import challenge.tech.domain.PaymentStatus;
import challenge.tech.dto.WebhookRequest;
import challenge.tech.services.JwtTokenService;
import challenge.tech.usecase.UpdatePaymentStatusUseCase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebhookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @MockitoBean
    private JwtTokenService jwtTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdatePaymentStatus() throws Exception {
        WebhookRequest request = new WebhookRequest();
        request.setTransactionId("12345");
        request.setStatus(PaymentStatus.SUCCESS);

        doNothing().when(updatePaymentStatusUseCase).execute(anyString(), any(PaymentStatus.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Service-Key", "secret-internal-key");

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        var response = restTemplate.postForEntity("/v1/payments/webhook", entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenProcessingInvalidWebhook() throws Exception {
        WebhookRequest request = new WebhookRequest();
        // Missing transactionId and status to make it invalid

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Service-Key", "secret-internal-key");

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        var response = restTemplate.postForEntity("/v1/payments/webhook", entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnInternalServerErrorWhenUpdatePaymentStatusUseCaseThrowsException() throws Exception {
        WebhookRequest request = new WebhookRequest();
        request.setTransactionId("12345");
        request.setStatus(PaymentStatus.SUCCESS);

        doThrow(new RuntimeException("Simulated internal error")).when(updatePaymentStatusUseCase).execute(anyString(),
                any(PaymentStatus.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Service-Key", "secret-internal-key");

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        var response = restTemplate.postForEntity("/v1/payments/webhook", entity, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}