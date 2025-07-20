package challenge.tech.controller;

import challenge.tech.domain.Stock;
import challenge.tech.dto.parameter.CreateStockParameter;
import challenge.tech.dto.parameter.UpdateStockParameter;
import challenge.tech.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private FindAllUseCase findAllUseCase;
    @Mock
    private FindByProductsIdUseCase findByProductsIdUseCase;
    @Mock
    private CreateUseCase createUseCase;
    @Mock
    private UpdateUseCase updateUseCase;
    @Mock
    private DeleteUseCase deleteUseCase;

    @InjectMocks
    private StockController stockController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAllStocks_shouldReturnPageOfStocks() throws Exception {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setProductId(101L);
        stock.setQuantity(50);
        List<Stock> stockList = Collections.singletonList(stock);
        Page<Stock> stockPage = new PageImpl<>(stockList, PageRequest.of(0, 10), 1);

        when(findAllUseCase.execute(any(Pageable.class))).thenReturn(stockPage);

        mockMvc.perform(get("/v1/stocks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productId").value(101L));
    }

    @Test
    void findByProductsId_shouldReturnStock() throws Exception {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setProductId(101L);
        stock.setQuantity(50);

        when(findByProductsIdUseCase.execute(anyLong())).thenReturn(stock);

        mockMvc.perform(get("/v1/stocks/products/{productId}", 101L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(101L));
    }

    @Test
    void create_shouldReturnCreatedStock() throws Exception {
        CreateStockParameter parameter = new CreateStockParameter(101L, 50);

        Stock createdStock = new Stock();
        createdStock.setId(1L);
        createdStock.setProductId(101L);
        createdStock.setQuantity(50);

        when(createUseCase.execute(any(Stock.class))).thenReturn(createdStock);

        mockMvc.perform(post("/v1/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(101L));
    }

    @Test
    void update_shouldReturnUpdatedStock() throws Exception {
        UpdateStockParameter parameter = new UpdateStockParameter();
        parameter.setProductId(101L);
        parameter.setQuantity(75);

        Stock updatedStock = new Stock();
        updatedStock.setId(1L);
        updatedStock.setProductId(101L);
        updatedStock.setQuantity(75);

        when(updateUseCase.execute(anyLong(), anyLong(), anyInt())).thenReturn(updatedStock);

        mockMvc.perform(put("/v1/stocks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(75));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(deleteUseCase).execute(anyLong());

        mockMvc.perform(delete("/v1/stocks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
