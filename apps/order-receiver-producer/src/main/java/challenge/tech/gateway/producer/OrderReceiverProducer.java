package challenge.tech.gateway.producer;


import challenge.tech.dto.OrderReceiverDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderReceiverProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${application.queue.order-receiver}")
    private String orderReceiverQueue;

    public void sendOrder(OrderReceiverDTO orderReceiver) {
        String orderJson = null;
        try {
            log.info("Sending order: {}", orderReceiver);
            orderJson = objectMapper.writeValueAsString(orderReceiver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Order JSON: {}", orderJson);
        rabbitTemplate.convertAndSend(orderReceiverQueue, orderJson);
        log.info("Order sent to queue: {}", orderReceiverQueue);
    }
}
