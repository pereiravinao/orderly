package challenge.tech.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderReceiver {
    private Long id;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;

    public void update(OrderReceiver stock) {
        if (stock == null) return;

        if (stock.getQuantity() != null) {
            this.quantity = stock.getQuantity();
        }
    }

    public OrderReceiver plusQuantity(Integer quantity) {
        this.quantity += quantity;
        return this;
    }
}
