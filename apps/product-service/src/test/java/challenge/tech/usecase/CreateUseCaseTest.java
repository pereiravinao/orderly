package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.exceptions.product.ProductAlreadyExistsException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUseCaseTest {

    @Mock
    private ProductJpaGateway productJpaGateway;

    @InjectMocks
    private CreateUseCase createUseCase;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setSku("TEST_SKU");
        product.setPrice(BigDecimal.valueOf(10.0));
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(productJpaGateway.existsBySku(any(String.class))).thenReturn(false);
        when(productJpaGateway.save(any(Product.class))).thenReturn(product);

        Product createdProduct = createUseCase.execute(product);

        assertNotNull(createdProduct);
        assertEquals("TEST_SKU", createdProduct.getSku());
        verify(productJpaGateway).existsBySku("TEST_SKU");
        verify(productJpaGateway).save(product);
    }

    @Test
    void shouldThrowExceptionWhenProductWithSameSkuAlreadyExists() {
        when(productJpaGateway.existsBySku(any(String.class))).thenReturn(true);

        ProductAlreadyExistsException exception = assertThrows(ProductAlreadyExistsException.class,
                () -> createUseCase.execute(product));

        assertEquals("Product with this SKU already exists", exception.getMessage());
        verify(productJpaGateway).existsBySku("TEST_SKU");
    }
}
