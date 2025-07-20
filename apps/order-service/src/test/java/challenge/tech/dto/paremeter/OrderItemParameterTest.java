package challenge.tech.dto.paremeter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemParameterTest {

    private OrderItemParameter orderItemParameter;

    @BeforeEach
    void setUp() {
        orderItemParameter = new OrderItemParameter();
    }

    @Test
    void testSetAndGetProductId() {
        Long productId = 1L;
        orderItemParameter.setProductId(productId);
        assertEquals(productId, orderItemParameter.getProductId());
    }

    @Test
    void testSetAndGetQuantity() {
        Integer quantity = 10;
        orderItemParameter.setQuantity(quantity);
        assertEquals(quantity, orderItemParameter.getQuantity());
    }
}
