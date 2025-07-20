package challenge.tech.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderReceiverTest {

    @Test
    void gettersAndSettersShouldWork() {
        // Given
        OrderReceiver orderReceiver = new OrderReceiver();
        Long id = 1L;
        Long productId = 10L;
        Integer quantity = 5;
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        orderReceiver.setId(id);
        orderReceiver.setProductId(productId);
        orderReceiver.setQuantity(quantity);
        orderReceiver.setCreatedAt(createdAt);

        // Then
        assertEquals(id, orderReceiver.getId());
        assertEquals(productId, orderReceiver.getProductId());
        assertEquals(quantity, orderReceiver.getQuantity());
        assertEquals(createdAt, orderReceiver.getCreatedAt());
    }

    @Test
    void updateShouldUpdateQuantityWhenNotNull() {
        // Given
        OrderReceiver existingOrderReceiver = new OrderReceiver();
        existingOrderReceiver.setQuantity(10);

        OrderReceiver newOrderReceiver = new OrderReceiver();
        newOrderReceiver.setQuantity(20);

        // When
        existingOrderReceiver.update(newOrderReceiver);

        // Then
        assertEquals(20, existingOrderReceiver.getQuantity());
    }

    @Test
    void updateShouldNotUpdateQuantityWhenNull() {
        // Given
        OrderReceiver existingOrderReceiver = new OrderReceiver();
        existingOrderReceiver.setQuantity(10);

        OrderReceiver newOrderReceiver = new OrderReceiver();
        newOrderReceiver.setQuantity(null);

        // When
        existingOrderReceiver.update(newOrderReceiver);

        // Then
        assertEquals(10, existingOrderReceiver.getQuantity());
    }

    @Test
    void updateShouldDoNothingWhenInputIsNull() {
        // Given
        OrderReceiver existingOrderReceiver = new OrderReceiver();
        existingOrderReceiver.setQuantity(10);

        // When
        existingOrderReceiver.update(null);

        // Then
        assertEquals(10, existingOrderReceiver.getQuantity());
    }

    @Test
    void plusQuantityShouldIncrementQuantity() {
        // Given
        OrderReceiver orderReceiver = new OrderReceiver();
        orderReceiver.setQuantity(5);

        // When
        orderReceiver.plusQuantity(3);

        // Then
        assertEquals(8, orderReceiver.getQuantity());
    }

    @Test
    void plusQuantityShouldReturnSameInstance() {
        // Given
        OrderReceiver orderReceiver = new OrderReceiver();
        orderReceiver.setQuantity(5);

        // When
        OrderReceiver result = orderReceiver.plusQuantity(3);

        // Then
        assertEquals(orderReceiver, result);
    }
}
