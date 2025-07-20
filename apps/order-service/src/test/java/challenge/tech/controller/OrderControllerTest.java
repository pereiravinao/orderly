package challenge.tech.controller;

import challenge.tech.domain.Order;
import challenge.tech.dto.paremeter.CreateOrderParameter;
import challenge.tech.usecase.CreateUseCase;
import challenge.tech.usecase.DeleteUseCase;
import challenge.tech.usecase.FindAllUseCase;
import challenge.tech.usecase.FindByIdUseCase;
import challenge.tech.dto.paremeter.OrderItemParameter;
import challenge.tech.usecase.UpdatePaymentUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import challenge.tech.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FindAllUseCase findAllUseCase;
    @MockitoBean
    private FindByIdUseCase findByIdUseCase;
    @MockitoBean
    private CreateUseCase createUseCase;
    @MockitoBean
    private DeleteUseCase deleteUseCase;
    @MockitoBean
    private UpdatePaymentUseCase updatePaymentUseCase;
    @MockitoBean
    private JwtTokenService jwtTokenService;

    @Test
    void shouldFindAllOrders() throws Exception {
        // Given
        when(findAllUseCase.execute(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Order())));

        // When & Then
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(findAllUseCase, times(1)).execute(any(Pageable.class));
    }

    @Test
    void shouldFindOrderById() throws Exception {
        // Given
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(findByIdUseCase.execute(orderId)).thenReturn(order);

        // When & Then
        mockMvc.perform(get("/v1/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderId));

        verify(findByIdUseCase, times(1)).execute(orderId);
    }

    @Test
    void shouldCreateOrder() throws Exception {
        // Given
        OrderItemParameter item = new OrderItemParameter();
        item.setProductId(1L);
        item.setQuantity(1);
        CreateOrderParameter parameter = new CreateOrderParameter(Collections.singletonList(item), 123L);

        Order createdOrder = new Order();
        createdOrder.setId(1L);
        when(createUseCase.execute(any(Order.class))).thenReturn(createdOrder);

        // When & Then
        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdOrder.getId()));

        verify(createUseCase, times(1)).execute(any(Order.class));
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        // Given
        Long orderId = 1L;
        doNothing().when(deleteUseCase).execute(orderId);

        // When & Then
        mockMvc.perform(delete("/v1/orders/{id}", orderId))
                .andExpect(status().isNoContent());

        verify(deleteUseCase, times(1)).execute(orderId);
    }
}
