package challenge.tech.dto.presenter;

import challenge.tech.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long paymentId;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<ProductOrderDetails> products;
}
