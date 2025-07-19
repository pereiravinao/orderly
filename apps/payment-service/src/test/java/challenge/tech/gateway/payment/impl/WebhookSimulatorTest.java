
package challenge.tech.gateway.payment.impl;

import challenge.tech.dto.WebhookRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WebhookSimulatorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WebhookSimulator webhookSimulator;

    @Test
    void shouldSimulateWebhook() {
        String transactionId = "12345";
        Long orderId = 1L;

        when(restTemplate.postForEntity(anyString(), any(WebhookRequest.class), any(Class.class)))
                .thenReturn(ResponseEntity.ok().build());

        webhookSimulator.simulateWebhook(transactionId, orderId);

        verify(restTemplate).postForEntity(anyString(), any(WebhookRequest.class), any(Class.class));
    }
}
