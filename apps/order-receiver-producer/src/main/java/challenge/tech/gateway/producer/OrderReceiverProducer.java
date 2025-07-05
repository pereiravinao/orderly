package challenge.tech.gateway.producer;


import challenge.tech.dto.OrderReceiverDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderReceiverProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${application.queue.order-receiver}")
    private String orderReceiverQueue;

    public void sendOrder(OrderReceiverDTO orderReceiver) {
        ObjectMapper mapper = new ObjectMapper();
        String orderJson = null;
        try {
            orderJson = mapper.writeValueAsString(orderReceiver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        rabbitTemplate.convertAndSend(orderReceiverQueue, orderJson);
    }
}
