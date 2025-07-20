package challenge.tech.gateway.database.jpa.entity;


import challenge.tech.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public ProductEntity(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.sku = product.getSku();
        this.price = product.getPrice();
        this.createdAt = product.getCreatedAt();
    }

    public Product toDomain() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setSku(this.sku);
        product.setPrice(this.price);
        product.setCreatedAt(this.createdAt);
        return product;
    }

}