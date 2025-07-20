package challenge.tech.usecase;

import challenge.tech.gateway.database.ProductJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUseCaseTest {

    @Mock
    private ProductJpaGateway productJpaGateway;

    @InjectMocks
    private DeleteUseCase deleteUseCase;

    @Test
    void shouldDeleteProductSuccessfully() {
        Long productId = 1L;
        deleteUseCase.execute(productId);
        verify(productJpaGateway, times(1)).deleteById(productId);
    }
}
