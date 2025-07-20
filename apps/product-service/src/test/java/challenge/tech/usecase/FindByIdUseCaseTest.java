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
class FindByIdUseCaseTest {

    @Mock
    private ProductJpaGateway productJpaGateway;

    @InjectMocks
    private FindByIdUseCase findByIdUseCase;

    @Test
    void shouldFindProductByIdSuccessfully() {
        when(productJpaGateway.findById(any(Long.class))).thenReturn(new Product());

        Product product = findByIdUseCase.execute(1L);

        assertNotNull(product);
    }

    @Test
    void shouldReturnNullWhenProductByIdDoesNotExist() {
        when(productJpaGateway.findById(any(Long.class))).thenReturn(null);

        Product product = findByIdUseCase.execute(1L);

        assertNull(product);
    }
}
