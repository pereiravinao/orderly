package challenge.tech.domain;

import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Order {
    private Long id;
    private List<OrderItem> products;
    private Long userId;
    private Long paymentId;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;

}
