package challenge.tech.usecase;

import challenge.tech.gateway.database.StockJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUseCaseTest {

    @Mock
    private StockJpaGateway stockJpaGateway;

    @InjectMocks
    private DeleteUseCase deleteUseCase;

    @Test
    void shouldCallDeleteByIdOnGateway() {
        Long stockId = 1L;
        doNothing().when(stockJpaGateway).deleteById(anyLong());

        deleteUseCase.execute(stockId);

        verify(stockJpaGateway, times(1)).deleteById(stockId);
    }
}
