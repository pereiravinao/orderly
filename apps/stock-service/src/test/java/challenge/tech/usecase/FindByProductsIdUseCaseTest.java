package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.StockJpaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByProductsIdUseCaseTest {

    @Mock
    private StockJpaGateway stockJpaGateway;

    @InjectMocks
    private FindByProductsIdUseCase findByProductsIdUseCase;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.setId(1L);
        stock.setProductId(101L);
        stock.setQuantity(50);
    }

    @Test
    void shouldReturnStockWhenFound() {
        when(stockJpaGateway.findByProductId(anyLong())).thenReturn(Optional.of(stock));

        Stock result = findByProductsIdUseCase.execute(101L);

        assertNotNull(result);
        assertEquals(stock.getProductId(), result.getProductId());
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        when(stockJpaGateway.findByProductId(anyLong())).thenReturn(Optional.empty());

        assertThrows(StockExceptionHandler.class, () -> findByProductsIdUseCase.execute(101L));
    }
}
