package challenge.tech.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        stock.setId(id);
        assertEquals(id, stock.getId());
    }

    @Test
    void testSetAndGetProductId() {
        Long productId = 101L;
        stock.setProductId(productId);
        assertEquals(productId, stock.getProductId());
    }

    @Test
    void testSetAndGetQuantity() {
        Integer quantity = 50;
        stock.setQuantity(quantity);
        assertEquals(quantity, stock.getQuantity());
    }

    @Test
    void testSetAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        stock.setCreatedAt(now);
        assertEquals(now, stock.getCreatedAt());
    }

    @Test
    void testUpdateMethod() {
        Stock newStockData = new Stock();
        newStockData.setQuantity(100);

        stock.setQuantity(50);
        stock.update(newStockData);
        assertEquals(100, stock.getQuantity());
    }

    @Test
    void testUpdateMethodWithNullStock() {
        stock.setQuantity(50);
        stock.update(null);
        assertEquals(50, stock.getQuantity()); // Quantity should remain unchanged
    }

    @Test
    void testUpdateMethodWithNullQuantity() {
        Stock newStockData = new Stock();
        newStockData.setQuantity(null);

        stock.setQuantity(50);
        stock.update(newStockData);
        assertEquals(50, stock.getQuantity()); // Quantity should remain unchanged
    }

    @Test
    void testPlusQuantity() {
        stock.setQuantity(50);
        stock.plusQuantity(20);
        assertEquals(70, stock.getQuantity());
    }
}
