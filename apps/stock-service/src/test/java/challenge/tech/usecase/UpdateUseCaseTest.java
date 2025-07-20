package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.StockJpaGateway;
import challenge.tech.service.ProductValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUseCaseTest {

    @Mock
    private StockJpaGateway stockJpaGateway;

    @Mock
    private ProductValidationService productValidationService;

    @InjectMocks
    private UpdateUseCase updateUseCase;

    private Stock existingStock;

    @BeforeEach
    void setUp() {
        existingStock = new Stock();
        existingStock.setId(1L);
        existingStock.setProductId(101L);
        existingStock.setQuantity(50);
    }

    @Test
    void shouldUpdateStockSuccessfully() {
        when(stockJpaGateway.findById(anyLong())).thenReturn(existingStock);
        doNothing().when(productValidationService).validateProductExistence(anyLong());
        when(stockJpaGateway.save(any(Stock.class))).thenReturn(existingStock);

        Stock result = updateUseCase.execute(1L, 101L, 20);

        assertNotNull(result);
        assertEquals(70, result.getQuantity());
        verify(stockJpaGateway, times(1)).findById(1L);
        verify(productValidationService, times(1)).validateProductExistence(101L);
        verify(stockJpaGateway, times(1)).save(existingStock);
    }

    @Test
    void shouldThrowExceptionWhenInsufficientStock() {
        when(stockJpaGateway.findById(anyLong())).thenReturn(existingStock);
        doNothing().when(productValidationService).validateProductExistence(anyLong());

        assertThrows(StockExceptionHandler.class, () -> updateUseCase.execute(1L, 101L, -60));

        verify(stockJpaGateway, times(1)).findById(1L);
        verify(productValidationService, times(1)).validateProductExistence(101L);
        verify(stockJpaGateway, never()).save(any(Stock.class));
    }

    @Test
    void shouldHandleProductNotFound() {
        when(stockJpaGateway.findById(anyLong())).thenThrow(StockExceptionHandler.notFound());

        assertThrows(StockExceptionHandler.class, () -> updateUseCase.execute(999L, 101L, 10));

        verify(stockJpaGateway, times(1)).findById(999L);
        verify(productValidationService, never()).validateProductExistence(anyLong());
        verify(stockJpaGateway, never()).save(any(Stock.class));
    }
}
