package challenge.tech.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public void update(Product product) {
        if (product == null) return;

        if (product.getName() != null) {
            this.name = product.getName();
        }
        if (product.getPrice() != null) {
            this.price = product.getPrice();
        }
        if (product.getSku() != null) {
            this.sku = product.getSku();
        }
    }
}
