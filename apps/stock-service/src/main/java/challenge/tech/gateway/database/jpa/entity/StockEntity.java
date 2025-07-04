package challenge.tech.gateway.database.jpa.entity;


import challenge.tech.domain.Stock;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_stock")
@Getter
@Setter
@NoArgsConstructor
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public StockEntity(Stock stock) {
        this.id = stock.getId();
        this.productId = stock.getProductId();
        this.quantity = stock.getQuantity();
        this.createdAt = stock.getCreatedAt();
    }

    public Stock toDomain() {
        Stock stock = new Stock();
        stock.setId(this.id);
        stock.setProductId(this.productId);
        stock.setQuantity(this.quantity);
        stock.setCreatedAt(this.createdAt);
        return stock;
    }

}