package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.gateway.database.ProductJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindBySkuUseCaseTest {

    @Mock
    private ProductJpaGateway productJpaGateway;

    @InjectMocks
    private FindBySkuUseCase findBySkuUseCase;

    @Test
    void shouldFindProductBySkuSuccessfully() {
        when(productJpaGateway.findBySku(any(String.class))).thenReturn(new Product());

        Product product = findBySkuUseCase.execute("TEST_SKU");

        assertNotNull(product);
    }

    @Test
    void shouldReturnNullWhenProductBySkuDoesNotExist() {
        when(productJpaGateway.findBySku(any(String.class))).thenReturn(null);

        Product product = findBySkuUseCase.execute("NON_EXISTENT_SKU");

        assertNull(product);
    }
}
