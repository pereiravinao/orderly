package challenge.tech.usecase;

import challenge.tech.client.ProductServiceFeignClient;
import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.dto.OrderReceiverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessOrderUseCase {
    private final CreateUseCase createUseCase;
    private final ProductServiceFeignClient productServiceFeignClient;

    public void execute(OrderReceiverDTO orderReceiverDTO) {
        Order order = new Order();
        order.setUserId(orderReceiverDTO.getUser().getId());
        order.setProducts(validateProducts(orderReceiverDTO));
        createUseCase.execute(order, orderReceiverDTO.getPayment().getCardNumber());
    }

    private List<OrderItem> validateProducts(OrderReceiverDTO orderReceiverDTO) {
        List<OrderItem> items = new ArrayList<>();
        orderReceiverDTO.getItems().forEach(item -> {
            var product = productServiceFeignClient.findBySku(item.getSku());
            items.add(buildOrderItem(product.getId(), item.getQuantity()));
        });
        return items;
    }

    private OrderItem buildOrderItem(Long productId, Integer quantity) {
        var orderItem = new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}
