package challenge.tech.dto.presenter;

import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long paymentId;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<ProductOrderDetails> products;

    public Order toDomain() {
        var order = new Order();
        order.setId(this.id);
        order.setUserId(this.userId);
        order.setPaymentId(this.paymentId);
        order.setStatus(this.status);
        order.setTotal(this.total);
        order.setCreatedAt(this.createdAt);
        order.setProducts(this.products.stream().map(product -> {
            var orderItem = new OrderItem();
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(product.getQuantity());
            return orderItem;
        }).toList());
        return order;
    }

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.total = order.getTotal();
        this.paymentId = order.getPaymentId();
        this.userId = order.getUserId();
    }

}
