package challenge.tech.listener;

import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.usecase.ProcessOrderUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderReceiverListenerTest {

    @Mock
    private ProcessOrderUseCase processOrderUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderReceiverListener orderReceiverListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessMessageSuccessfully() throws Exception {
        // Given
        String message = "{\"id\":1,\"items\":[],\"payment\":{},\"order\":{},\"user\":{}}";
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();

        when(objectMapper.readValue(eq(message), eq(OrderReceiverDTO.class))).thenReturn(orderReceiverDTO);

        // When
        orderReceiverListener.orderReceiver(message);

        // Then
        verify(objectMapper, times(1)).readValue(eq(message), eq(OrderReceiverDTO.class));
        verify(processOrderUseCase, times(1)).execute(orderReceiverDTO);
    }

    @Test
    void shouldHandleExceptionWhenProcessingMessage() throws Exception {
        // Given
        String message = "invalid json";

        when(objectMapper.readValue(eq(message), eq(OrderReceiverDTO.class))).thenThrow(new RuntimeException("JSON parsing error"));

        // When
        orderReceiverListener.orderReceiver(message);

        // Then
        verify(objectMapper, times(1)).readValue(eq(message), eq(OrderReceiverDTO.class));
        verify(processOrderUseCase, never()).execute(any(OrderReceiverDTO.class));
        // No assertion for e.printStackTrace() as it's a side effect, but we ensure it doesn't crash
    }
}
