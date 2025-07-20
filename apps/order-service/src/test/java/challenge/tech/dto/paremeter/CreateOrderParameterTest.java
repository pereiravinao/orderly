package challenge.tech.dto.paremeter;

import challenge.tech.domain.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateOrderParameterTest {

    @Test
    void toDomainShouldMapCreateOrderParameterToOrder() {
        OrderItemParameter item1 = new OrderItemParameter();
        item1.setProductId(1L);
        item1.setQuantity(2);
        List<OrderItemParameter> products = List.of(item1);

        CreateOrderParameter createOrderParameter = new CreateOrderParameter(products, 101L);

        Order order = createOrderParameter.toDomain();

        assertNotNull(order);
        assertEquals(101L, order.getPaymentId());
        assertNotNull(order.getProducts());
        assertEquals(1, order.getProducts().size());
        assertEquals(1L, order.getProducts().getFirst().getProductId());
        assertEquals(2, order.getProducts().getFirst().getQuantity());
    }

    @Test
    void testGetProducts() {
        OrderItemParameter item1 = new OrderItemParameter();
        item1.setProductId(1L);
        item1.setQuantity(2);
        List<OrderItemParameter> products = List.of(item1);
        CreateOrderParameter createOrderParameter = new CreateOrderParameter(products, 101L);
        assertEquals(products, createOrderParameter.getProducts());
    }

    @Test
    void testGetPaymentId() {
        OrderItemParameter item1 = new OrderItemParameter();
        item1.setProductId(1L);
        item1.setQuantity(2);
        List<OrderItemParameter> products = List.of(item1);
        CreateOrderParameter createOrderParameter = new CreateOrderParameter(products, 101L);
        assertEquals(101L, createOrderParameter.getPaymentId());
    }
}
