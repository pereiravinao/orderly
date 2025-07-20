package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductEntityTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setSku("TEST_SKU");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setCreatedAt(LocalDateTime.now().minusDays(1));
    }

    @Test
    void shouldConstructProductEntityFromProductDomain() {
        ProductEntity productEntity = new ProductEntity(product);

        assertEquals(product.getId(), productEntity.getId());
        assertEquals(product.getName(), productEntity.getName());
        assertEquals(product.getSku(), productEntity.getSku());
        assertEquals(product.getPrice(), productEntity.getPrice());
        assertEquals(product.getCreatedAt(), productEntity.getCreatedAt());
    }

    @Test
    void shouldConvertProductEntityToProductDomain() {
        ProductEntity productEntity = new ProductEntity(product);
        Product domainProduct = productEntity.toDomain();

        assertEquals(productEntity.getId(), domainProduct.getId());
        assertEquals(productEntity.getName(), domainProduct.getName());
        assertEquals(productEntity.getSku(), domainProduct.getSku());
        assertEquals(productEntity.getPrice(), domainProduct.getPrice());
        assertEquals(productEntity.getCreatedAt(), domainProduct.getCreatedAt());
    }

    @Test
    void prePersistShouldSetCreatedAtIfNull() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(2L);
        productEntity.setName("New Product");
        productEntity.setSku("NEW_SKU");
        productEntity.setPrice(BigDecimal.valueOf(20.0));
        assertNull(productEntity.getCreatedAt());

        productEntity.prePersist();

        assertNotNull(productEntity.getCreatedAt());
    }

    @Test
    void prePersistShouldNotChangeCreatedAtIfNotNull() {
        LocalDateTime originalCreatedAt = LocalDateTime.now().minusHours(5);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(3L);
        productEntity.setName("Another Product");
        productEntity.setSku("ANOTHER_SKU");
        productEntity.setPrice(BigDecimal.valueOf(30.0));
        productEntity.setCreatedAt(originalCreatedAt);

        productEntity.prePersist();

        assertEquals(originalCreatedAt, productEntity.getCreatedAt());
    }
}
