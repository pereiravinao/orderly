package challenge.tech.gateway.database;

import challenge.tech.domain.Stock;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.StockEntity;
import challenge.tech.gateway.database.jpa.repository.StockRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockJpaGatewayTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockJpaGateway stockJpaGateway;

    private Stock stock;
    private StockEntity stockEntity;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.setId(1L);
        stock.setProductId(101L);
        stock.setQuantity(50);

        stockEntity = new StockEntity(stock);
    }

    @Test
    void findAllShouldReturnPageOfStocks() {
        List<StockEntity> stockEntityList = Collections.singletonList(stockEntity);
        Page<StockEntity> stockEntityPage = new PageImpl<>(stockEntityList, PageRequest.of(0, 10), 1);

        when(stockRepository.findAll(any(Pageable.class))).thenReturn(stockEntityPage);

        Page<Stock> result = stockJpaGateway.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(stock.getProductId(), result.getContent().get(0).getProductId());
    }

    @Test
    void findByIdShouldReturnStockWhenFound() {
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stockEntity));

        Stock result = stockJpaGateway.findById(1L);

        assertNotNull(result);
        assertEquals(stock.getId(), result.getId());
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(stockRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StockExceptionHandler.class, () -> stockJpaGateway.findById(1L));
    }

    @Test
    void saveShouldReturnSavedStock() {
        when(stockRepository.save(any(StockEntity.class))).thenReturn(stockEntity);

        Stock result = stockJpaGateway.save(stock);

        assertNotNull(result);
        assertEquals(stock.getId(), result.getId());
    }

    @Test
    void deleteByIdShouldCallRepositoryDeleteById() {
        doNothing().when(stockRepository).deleteById(anyLong());

        stockJpaGateway.deleteById(1L);

        verify(stockRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByProductIdShouldReturnStockWhenFound() {
        when(stockRepository.findByProductId(anyLong())).thenReturn(Optional.of(stockEntity));

        Optional<Stock> result = stockJpaGateway.findByProductId(101L);

        assertTrue(result.isPresent());
        assertEquals(stock.getProductId(), result.get().getProductId());
    }

    @Test
    void findByProductIdShouldReturnEmptyOptionalWhenNotFound() {
        when(stockRepository.findByProductId(anyLong())).thenReturn(Optional.empty());

        Optional<Stock> result = stockJpaGateway.findByProductId(101L);

        assertFalse(result.isPresent());
    }
}
