package challange.tech.gateway.database.jpa.entity;


import challange.tech.domain.Order;
import challange.tech.enums.OrderStatus;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tb_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, Integer>> products;

    private Long paymentId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal total;

    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = OrderStatus.ABERTO;
        }
    }

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.products = order.getProducts();
        this.paymentId = order.getPaymentId();
        this.userId = order.getUserId();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.createdAt = order.getCreatedAt();
    }

    public Order toDomain() {
        Order order = new Order();
        order.setId(this.id);
        order.setProducts(this.products);
        order.setPaymentId(this.paymentId);
        order.setUserId(this.userId);
        order.setStatus(this.status);
        order.setTotal(this.total);
        order.setCreatedAt(this.createdAt);
        return order;
    }

}