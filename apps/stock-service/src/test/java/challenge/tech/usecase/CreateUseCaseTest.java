package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.gateway.database.StockJpaGateway;
import challenge.tech.service.ProductValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUseCaseTest {

    @Mock
    private StockJpaGateway stockJpaGateway;

    @Mock
    private ProductValidationService productValidationService;

    @InjectMocks
    private CreateUseCase createUseCase;

    private Stock stockParameter;

    @BeforeEach
    void setUp() {
        stockParameter = new Stock();
        stockParameter.setProductId(101L);
        stockParameter.setQuantity(50);
    }

    @Test
    void shouldCreateNewStockWhenProductDoesNotExist() {
        doNothing().when(productValidationService).validateProductExistence(anyLong());
        when(stockJpaGateway.findByProductId(anyLong())).thenReturn(Optional.empty());
        when(stockJpaGateway.save(any(Stock.class))).thenReturn(stockParameter);

        Stock result = createUseCase.execute(stockParameter);

        assertNotNull(result);
        assertEquals(stockParameter.getProductId(), result.getProductId());
        assertEquals(stockParameter.getQuantity(), result.getQuantity());
        verify(productValidationService, times(1)).validateProductExistence(anyLong());
        verify(stockJpaGateway, times(1)).findByProductId(anyLong());
        verify(stockJpaGateway, times(1)).save(any(Stock.class));
    }

    @Test
    void shouldUpdateExistingStockWhenProductExists() {
        Stock existingStock = new Stock();
        existingStock.setProductId(101L);
        existingStock.setQuantity(20);

        doNothing().when(productValidationService).validateProductExistence(anyLong());
        when(stockJpaGateway.findByProductId(anyLong())).thenReturn(Optional.of(existingStock));
        when(stockJpaGateway.save(any(Stock.class))).thenReturn(existingStock);

        Stock result = createUseCase.execute(stockParameter);

        assertNotNull(result);
        assertEquals(stockParameter.getProductId(), result.getProductId());
        assertEquals(70, result.getQuantity()); // 20 (existing) + 50 (parameter)
        verify(productValidationService, times(1)).validateProductExistence(anyLong());
        verify(stockJpaGateway, times(1)).findByProductId(anyLong());
        verify(stockJpaGateway, times(1)).save(any(Stock.class));
    }
}
