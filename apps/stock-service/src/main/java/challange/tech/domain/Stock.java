package challange.tech.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Stock {
    private Long id;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;

    public void update(Stock stock) {
        if (stock == null) return;

        if (stock.getQuantity() != null) {
            this.quantity = stock.getQuantity();
        }
    }

    public Stock plusQuantity(Integer quantity) {
        this.quantity += quantity;
        return this;
    }
}
