package challenge.tech.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemTest {

    @Test
    void constructorAndGettersShouldWork() {
        // Given
        String sku = "SKU001";
        Integer quantity = 5;

        // When
        OrderItem orderItem = new OrderItem(sku, quantity);

        // Then
        assertEquals(sku, orderItem.getSku());
        assertEquals(quantity, orderItem.getQuantity());
    }

    @Test
    void settersShouldWork() {
        // Given
        OrderItem orderItem = new OrderItem();
        String sku = "SKU002";
        Integer quantity = 10;

        // When
        orderItem.setSku(sku);
        orderItem.setQuantity(quantity);

        // Then
        assertEquals(sku, orderItem.getSku());
        assertEquals(quantity, orderItem.getQuantity());
    }
}
