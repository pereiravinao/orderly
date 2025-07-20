package challenge.tech.dto.presenter;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductOrderDetailsTest {

    @Test
    void constructorAndGettersShouldWork() {
        // Given
        Long productId = 1L;
        String name = "Test Product";
        BigDecimal unitPrice = BigDecimal.valueOf(10.50);
        Integer quantity = 2;

        // When
        ProductOrderDetails productOrderDetails = new ProductOrderDetails(productId, name, unitPrice, quantity);

        // Then
        assertEquals(productId, productOrderDetails.getProductId());
        assertEquals(name, productOrderDetails.getName());
        assertEquals(unitPrice, productOrderDetails.getUnitPrice());
        assertEquals(quantity, productOrderDetails.getQuantity());
    }

    @Test
    void settersShouldWork() {
        // Given
        ProductOrderDetails productOrderDetails = new ProductOrderDetails(null, null, null, null);

        Long productId = 2L;
        String name = "Another Product";
        BigDecimal unitPrice = BigDecimal.valueOf(20.00);
        Integer quantity = 5;

        // When
        productOrderDetails.setProductId(productId);
        productOrderDetails.setName(name);
        productOrderDetails.setUnitPrice(unitPrice);
        productOrderDetails.setQuantity(quantity);

        // Then
        assertEquals(productId, productOrderDetails.getProductId());
        assertEquals(name, productOrderDetails.getName());
        assertEquals(unitPrice, productOrderDetails.getUnitPrice());
        assertEquals(quantity, productOrderDetails.getQuantity());
    }
}
