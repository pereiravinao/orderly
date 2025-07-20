package challenge.tech.domain;

import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        order.setId(id);
        assertEquals(id, order.getId());
    }

    @Test
    void testSetAndGetProducts() {
        List<OrderItem> products = new ArrayList<>();
        products.add(new OrderItem());
        order.setProducts(products);
        assertEquals(products, order.getProducts());
    }

    @Test
    void testSetAndGetUserId() {
        Long userId = 101L;
        order.setUserId(userId);
        assertEquals(userId, order.getUserId());
    }

    @Test
    void testSetAndGetPaymentId() {
        Long paymentId = 202L;
        order.setPaymentId(paymentId);
        assertEquals(paymentId, order.getPaymentId());
    }

    @Test
    void testSetAndGetStatus() {
        OrderStatus status = OrderStatus.AGUARDANDO_PAGAMENTO;
        order.setStatus(status);
        assertEquals(status, order.getStatus());
    }

    @Test
    void testSetAndGetTotal() {
        BigDecimal total = new BigDecimal("100.00");
        order.setTotal(total);
        assertEquals(total, order.getTotal());
    }

    @Test
    void testSetAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);
        assertEquals(now, order.getCreatedAt());
    }
}
