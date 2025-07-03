package challange.tech.domain;

import challange.tech.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Order {
    private Long id;
    private List<Map<String, Integer>> products;
    private Long userId;
    private Long paymentId;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;

}
