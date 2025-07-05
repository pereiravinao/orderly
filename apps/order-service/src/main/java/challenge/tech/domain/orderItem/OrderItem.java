package challenge.tech.domain.orderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    private Long productId;
    private Integer quantity;

    public OrderItem(Long id, Integer quantity) {
        this.productId = id;
        this.quantity = quantity;
    }
}
