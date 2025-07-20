package challenge.tech.gateway.producer;

import challenge.tech.dto.OrderReceiverDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderReceiverProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderReceiverProducer orderReceiverProducer;

    private String testQueueName = "test-order-queue";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(orderReceiverProducer, "orderReceiverQueue", testQueueName);
    }

    @Test
    void shouldSendOrderSuccessfully() throws JsonProcessingException {
        // Given
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();
        String orderJson = "{}";

        when(objectMapper.writeValueAsString(orderReceiverDTO)).thenReturn(orderJson);

        // When
        orderReceiverProducer.sendOrder(orderReceiverDTO);

        // Then
        verify(objectMapper, times(1)).writeValueAsString(orderReceiverDTO);
        verify(rabbitTemplate, times(1)).convertAndSend(testQueueName, orderJson);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenJsonProcessingExceptionOccurs() throws JsonProcessingException {
        // Given
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();

        when(objectMapper.writeValueAsString(orderReceiverDTO)).thenThrow(new JsonProcessingException("Error") {});

        // When & Then
        assertThrows(RuntimeException.class, () -> orderReceiverProducer.sendOrder(orderReceiverDTO));
        verify(objectMapper, times(1)).writeValueAsString(orderReceiverDTO);
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString());
    }
}
