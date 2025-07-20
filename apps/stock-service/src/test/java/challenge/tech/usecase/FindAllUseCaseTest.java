package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.gateway.database.StockJpaGateway;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUseCaseTest {

    @Mock
    private StockJpaGateway stockJpaGateway;

    @InjectMocks
    private FindAllUseCase findAllUseCase;

    @Test
    void shouldReturnPageOfStocks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Stock> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(stockJpaGateway.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<Stock> result = findAllUseCase.execute(pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
    }
}
