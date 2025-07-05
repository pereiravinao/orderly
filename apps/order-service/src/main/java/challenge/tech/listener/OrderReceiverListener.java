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
public class OrderReceiverListener {

    private final ProcessOrderUseCase processOrderUseCase;

    @RabbitListener(queues = "order-receiver-queue")
    public void orderReceiver(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            var orderReceiver = mapper.readValue(message, OrderReceiverDTO.class);
            processOrderUseCase.execute(orderReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
