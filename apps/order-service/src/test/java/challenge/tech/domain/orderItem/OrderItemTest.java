package challenge.tech.domain.orderItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemTest {

    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
    }

    @Test
    void testNoArgsConstructor() {
        OrderItem newOrderItem = new OrderItem();
        assertEquals(null, newOrderItem.getProductId());
        assertEquals(null, newOrderItem.getQuantity());
    }

    @Test
    void testAllArgsConstructor() {
        Long productId = 1L;
        Integer quantity = 10;
        OrderItem newOrderItem = new OrderItem(productId, quantity);
        assertEquals(productId, newOrderItem.getProductId());
        assertEquals(quantity, newOrderItem.getQuantity());
    }

    @Test
    void testSetAndGetProductId() {
        Long productId = 2L;
        orderItem.setProductId(productId);
        assertEquals(productId, orderItem.getProductId());
    }

    @Test
    void testSetAndGetQuantity() {
        Integer quantity = 20;
        orderItem.setQuantity(quantity);
        assertEquals(quantity, orderItem.getQuantity());
    }
}
