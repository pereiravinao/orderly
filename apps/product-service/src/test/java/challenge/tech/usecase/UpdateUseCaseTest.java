package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.gateway.database.ProductJpaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class
UpdateUseCaseTest {

    @Mock
    private ProductJpaGateway productJpaGateway;

    @InjectMocks
    private UpdateUseCase updateUseCase;

    private Product existingProduct;
    private Product updatedProductData;

    @BeforeEach
    void setUp() {
        existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Original Product");
        existingProduct.setSku("ORIGINAL_SKU");
        existingProduct.setPrice(BigDecimal.valueOf(10.0));

        updatedProductData = new Product();
        updatedProductData.setName("Updated Product");
        updatedProductData.setSku("UPDATED_SKU");
        updatedProductData.setPrice(BigDecimal.valueOf(20.0));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        when(productJpaGateway.findById(any(Long.class))).thenReturn(existingProduct);
        when(productJpaGateway.save(any(Product.class))).thenReturn(existingProduct);

        Product result = updateUseCase.execute(1L, updatedProductData);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals("UPDATED_SKU", result.getSku());
        assertEquals(BigDecimal.valueOf(20.0), result.getPrice());
    }

    @Test
    void shouldReturnNullWhenProductToUpdateDoesNotExist() {
        when(productJpaGateway.findById(any(Long.class))).thenReturn(null);

        Product result = updateUseCase.execute(999L, updatedProductData);

        assertNull(result);
    }
}
