package challenge.tech.listener;

import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.usecase.ProcessOrderUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderReceiverListener {

    private final ProcessOrderUseCase processOrderUseCase;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "order-receiver-queue")
    public void orderReceiver(String message) {
        try {
            var orderReceiver = objectMapper.readValue(message, OrderReceiverDTO.class);
            log.info("Received order: {}", orderReceiver);
            processOrderUseCase.execute(orderReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
