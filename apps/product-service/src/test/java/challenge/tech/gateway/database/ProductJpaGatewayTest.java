package challenge.tech.gateway.database;

import challenge.tech.domain.Product;
import challenge.tech.exceptions.product.ProductNotFoundException;
import challenge.tech.gateway.database.jpa.entity.ProductEntity;
import challenge.tech.gateway.database.jpa.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductJpaGatewayTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductJpaGateway productJpaGateway;

    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setSku("TEST_SKU");
        product.setPrice(BigDecimal.valueOf(10.0));

        productEntity = new ProductEntity(product);
    }

    @Test
    void shouldFindAllProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> entityPage = new PageImpl<>(Collections.singletonList(productEntity), pageable, 1);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

        Page<Product> result = productJpaGateway.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getSku(), result.getContent().get(0).getSku());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindProductById() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));

        Product result = productJpaGateway.findById(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductByIdNotFound() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productJpaGateway.findById(1L));

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveProduct() {
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        Product result = productJpaGateway.save(product);

        assertNotNull(result);
        assertEquals(product.getSku(), result.getSku());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void shouldDeleteProductById() {
        productJpaGateway.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnTrueWhenProductExistsBySku() {
        when(productRepository.existsBySku(any(String.class))).thenReturn(true);

        boolean exists = productJpaGateway.existsBySku("TEST_SKU");

        assertTrue(exists);
        verify(productRepository, times(1)).existsBySku("TEST_SKU");
    }

    @Test
    void shouldReturnFalseWhenProductDoesNotExistBySku() {
        when(productRepository.existsBySku(any(String.class))).thenReturn(false);

        boolean exists = productJpaGateway.existsBySku("NON_EXISTENT_SKU");

        assertFalse(exists);
        verify(productRepository, times(1)).existsBySku("NON_EXISTENT_SKU");
    }

    @Test
    void shouldFindProductBySku() {
        when(productRepository.findBySku(any(String.class))).thenReturn(Optional.of(productEntity));

        Product result = productJpaGateway.findBySku("TEST_SKU");

        assertNotNull(result);
        assertEquals(product.getSku(), result.getSku());
        verify(productRepository, times(1)).findBySku("TEST_SKU");
    }

    @Test
    void shouldThrowExceptionWhenProductBySkuNotFound() {
        when(productRepository.findBySku(any(String.class))).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productJpaGateway.findBySku("NON_EXISTENT_SKU"));

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findBySku("NON_EXISTENT_SKU");
    }
}
