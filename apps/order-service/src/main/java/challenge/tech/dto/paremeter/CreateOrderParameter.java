package challenge.tech.dto.paremeter;

import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateOrderParameter {

    @NotNull
    private List<OrderItemParameter> products;

    @NotNull
    private Long paymentId;

    public Order toDomain() {
        Order order = new Order();
        order.setProducts(products.stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            return orderItem;
        }).collect(Collectors.toList()));
        order.setPaymentId(paymentId);
        return order;
    }

}
