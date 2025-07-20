package challenge.tech.controller;

import challenge.tech.dto.webhook.PaymentWebhookRequest;
import challenge.tech.enums.PaymentStatus;
import challenge.tech.usecase.UpdateOrderStatusFromWebhookUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import challenge.tech.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentWebhookController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentWebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UpdateOrderStatusFromWebhookUseCase updateOrderStatusFromWebhookUseCase;
    @MockBean
    private JwtTokenService jwtTokenService;

    @Test
    void shouldReceivePaymentWebhookSuccessfully() throws Exception {
        // Given
        Long orderId = 123L;
        PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
        PaymentWebhookRequest request = new PaymentWebhookRequest();
        request.setOrderId(orderId);
        request.setStatus(paymentStatus);

        doNothing().when(updateOrderStatusFromWebhookUseCase).execute(eq(orderId), eq(paymentStatus));

        // When & Then
        mockMvc.perform(post("/v1/orders/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(updateOrderStatusFromWebhookUseCase, times(1)).execute(eq(orderId), eq(paymentStatus));
    }
}
