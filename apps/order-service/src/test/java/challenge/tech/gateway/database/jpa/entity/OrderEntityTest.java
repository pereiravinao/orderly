package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    @Test
    void constructorShouldMapDomainToEntity() {
        // Given
        Order order = new Order();
        order.setId(1L);
        order.setProducts(Collections.singletonList(new OrderItem(10L, 2)));
        order.setPaymentId(100L);
        order.setUserId(5L);
        order.setStatus(OrderStatus.ABERTO);
        order.setTotal(BigDecimal.valueOf(200.00));
        order.setCreatedAt(LocalDateTime.now());

        // When
        OrderEntity orderEntity = new OrderEntity(order);

        // Then
        assertEquals(order.getId(), orderEntity.getId());
        assertEquals(order.getProducts(), orderEntity.getProducts());
        assertEquals(order.getPaymentId(), orderEntity.getPaymentId());
        assertEquals(order.getUserId(), orderEntity.getUserId());
        assertEquals(order.getStatus(), orderEntity.getStatus());
        assertEquals(order.getTotal(), orderEntity.getTotal());
        assertEquals(order.getCreatedAt(), orderEntity.getCreatedAt());
    }

    @Test
    void toDomainShouldMapEntityToDomain() {
        // Given
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setProducts(Collections.singletonList(new OrderItem(10L, 2)));
        orderEntity.setPaymentId(100L);
        orderEntity.setUserId(5L);
        orderEntity.setStatus(OrderStatus.ABERTO);
        orderEntity.setTotal(BigDecimal.valueOf(200.00));
        orderEntity.setCreatedAt(LocalDateTime.now());

        // When
        Order order = orderEntity.toDomain();

        // Then
        assertEquals(orderEntity.getId(), order.getId());
        assertEquals(orderEntity.getProducts(), order.getProducts());
        assertEquals(orderEntity.getPaymentId(), order.getPaymentId());
        assertEquals(orderEntity.getUserId(), order.getUserId());
        assertEquals(orderEntity.getStatus(), order.getStatus());
        assertEquals(orderEntity.getTotal(), order.getTotal());
        assertEquals(orderEntity.getCreatedAt(), order.getCreatedAt());
    }

    @Test
    void prePersistShouldSetCreatedAtAndStatusIfNull() {
        // Given
        OrderEntity orderEntity = new OrderEntity();
        assertNull(orderEntity.getCreatedAt());
        assertNull(orderEntity.getStatus());

        // When
        orderEntity.prePersist();

        // Then
        assertNotNull(orderEntity.getCreatedAt());
        assertEquals(OrderStatus.ABERTO, orderEntity.getStatus());
    }

    @Test
    void prePersistShouldNotOverwriteExistingCreatedAtAndStatus() {
        // Given
        OrderEntity orderEntity = new OrderEntity();
        LocalDateTime existingTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        orderEntity.setCreatedAt(existingTime);
        orderEntity.setStatus(OrderStatus.FECHADO_COM_SUCESSO);

        // When
        orderEntity.prePersist();

        // Then
        assertEquals(existingTime, orderEntity.getCreatedAt());
        assertEquals(OrderStatus.FECHADO_COM_SUCESSO, orderEntity.getStatus());
    }

    @Test
    void gettersAndSettersShouldWork() {
        OrderEntity orderEntity = new OrderEntity();
        Long id = 1L;
        List<OrderItem> products = Collections.singletonList(new OrderItem(10L, 1));
        Long paymentId = 100L;
        Long userId = 5L;
        OrderStatus status = OrderStatus.ABERTO;
        BigDecimal total = BigDecimal.valueOf(150.00);
        LocalDateTime createdAt = LocalDateTime.now();

        orderEntity.setId(id);
        orderEntity.setProducts(products);
        orderEntity.setPaymentId(paymentId);
        orderEntity.setUserId(userId);
        orderEntity.setStatus(status);
        orderEntity.setTotal(total);
        orderEntity.setCreatedAt(createdAt);

        assertEquals(id, orderEntity.getId());
        assertEquals(products, orderEntity.getProducts());
        assertEquals(paymentId, orderEntity.getPaymentId());
        assertEquals(userId, orderEntity.getUserId());
        assertEquals(status, orderEntity.getStatus());
        assertEquals(total, orderEntity.getTotal());
        assertEquals(createdAt, orderEntity.getCreatedAt());
    }
}
