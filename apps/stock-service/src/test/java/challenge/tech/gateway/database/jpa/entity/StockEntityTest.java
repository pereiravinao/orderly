package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StockEntityTest {

    private StockEntity stockEntity;

    @BeforeEach
    void setUp() {
        stockEntity = new StockEntity();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        stockEntity.setId(id);
        assertEquals(id, stockEntity.getId());
    }

    @Test
    void testSetAndGetProductId() {
        Long productId = 101L;
        stockEntity.setProductId(productId);
        assertEquals(productId, stockEntity.getProductId());
    }

    @Test
    void testSetAndGetQuantity() {
        Integer quantity = 50;
        stockEntity.setQuantity(quantity);
        assertEquals(quantity, stockEntity.getQuantity());
    }

    @Test
    void testSetAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        stockEntity.setCreatedAt(now);
        assertEquals(now, stockEntity.getCreatedAt());
    }

    @Test
    void prePersistShouldSetCreatedAtIfNull() {
        assertNull(stockEntity.getCreatedAt());
        stockEntity.prePersist();
        assertNotNull(stockEntity.getCreatedAt());
    }

    @Test
    void prePersistShouldNotChangeCreatedAtIfNotNull() {
        LocalDateTime initialTime = LocalDateTime.of(2023, 1, 1, 10, 0, 0);
        stockEntity.setCreatedAt(initialTime);
        stockEntity.prePersist();
        assertEquals(initialTime, stockEntity.getCreatedAt());
    }

    @Test
    void constructorShouldMapStockToStockEntity() {
        Stock stock = new Stock();
        stock.setId(2L);
        stock.setProductId(202L);
        stock.setQuantity(100);
        LocalDateTime now = LocalDateTime.now();
        stock.setCreatedAt(now);

        StockEntity newStockEntity = new StockEntity(stock);

        assertEquals(stock.getId(), newStockEntity.getId());
        assertEquals(stock.getProductId(), newStockEntity.getProductId());
        assertEquals(stock.getQuantity(), newStockEntity.getQuantity());
        assertEquals(stock.getCreatedAt(), newStockEntity.getCreatedAt());
    }

    @Test
    void toDomainShouldMapStockEntityToStock() {
        stockEntity.setId(3L);
        stockEntity.setProductId(303L);
        stockEntity.setQuantity(150);
        LocalDateTime now = LocalDateTime.now();
        stockEntity.setCreatedAt(now);

        Stock domainStock = stockEntity.toDomain();

        assertEquals(stockEntity.getId(), domainStock.getId());
        assertEquals(stockEntity.getProductId(), domainStock.getProductId());
        assertEquals(stockEntity.getQuantity(), domainStock.getQuantity());
        assertEquals(stockEntity.getCreatedAt(), domainStock.getCreatedAt());
    }
}
