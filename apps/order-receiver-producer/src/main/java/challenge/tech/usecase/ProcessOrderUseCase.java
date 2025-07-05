package challenge.tech.usecase;

import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.dto.UserDTO;
import challenge.tech.gateway.producer.OrderReceiverProducer;
import challenge.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessOrderUseCase {
    private final OrderReceiverProducer orderReceiverProducer;

    public void execute(OrderReceiverDTO orderReceiverDTO) {
        var currentUser = SecurityUtils.getCurrentUser();
        orderReceiverDTO.setUser(new UserDTO(currentUser.getId()));
        orderReceiverProducer.sendOrder(orderReceiverDTO);
    }
}
