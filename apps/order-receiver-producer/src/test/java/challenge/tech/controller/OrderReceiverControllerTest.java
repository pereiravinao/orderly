package challenge.tech.controller;

import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.services.JwtTokenService;
import challenge.tech.usecase.ProcessOrderUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderReceiverController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderReceiverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProcessOrderUseCase processOrderUseCase;
    @MockitoBean
    private JwtTokenService jwtTokenService;

    @Test
    void shouldReceiveOrderSuccessfully() throws Exception {
        // Given
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();
        // Populate orderReceiverDTO with some data if needed for serialization
        // orderReceiverDTO.setId(1L);

        doNothing().when(processOrderUseCase).execute(any(OrderReceiverDTO.class));

        // When & Then
        mockMvc.perform(post("/v1/orders-receivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderReceiverDTO)))
                .andExpect(status().isCreated());

        verify(processOrderUseCase, times(1)).execute(any(OrderReceiverDTO.class));
    }
}
