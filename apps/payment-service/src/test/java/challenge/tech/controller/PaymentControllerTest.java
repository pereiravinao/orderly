
package challenge.tech.controller;

import challenge.tech.domain.Payment;
import challenge.tech.domain.PaymentStatus;
import challenge.tech.dto.PaymentRequest;
import challenge.tech.usecase.ProcessPaymentUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import challenge.tech.services.JwtTokenService;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProcessPaymentUseCase processPaymentUseCase;

    @MockitoBean
    private JwtTokenService jwtTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    void shouldProcessPayment() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.SUCCESS);

        when(processPaymentUseCase.execute(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBadRequestWhenProcessingInvalidPayment() throws Exception {
        PaymentRequest request = new PaymentRequest();
        // Missing orderId and amount to make it invalid

        mockMvc.perform(post("/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnInternalServerErrorWhenProcessPaymentUseCaseThrowsException() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.0));

        when(processPaymentUseCase.execute(any(Payment.class))).thenThrow(new RuntimeException("Simulated internal error"));

        mockMvc.perform(post("/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
