package challenge.tech.usecase;

import challenge.tech.client.PaymentServiceFeignClient;
import challenge.tech.client.ProductServiceFeignClient;
import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.client.dto.PaymentRequest;
import challenge.tech.client.dto.PaymentResponse;
import challenge.tech.client.dto.StockDto;
import challenge.tech.client.dto.UpdateStockParameter;
import challenge.tech.domain.Order;
import challenge.tech.dto.OrderItemDTO;
import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.dto.PaymentDTO;
import challenge.tech.dto.UserDTO;
import challenge.tech.dto.presenter.Product;
import challenge.tech.enums.OrderStatus;
import challenge.tech.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessOrderUseCaseTest {

    @Mock
    private CreateUseCase createUseCase;
    @Mock
    private ProductServiceFeignClient productServiceFeignClient;
    @Mock
    private StockServiceFeignClient stockServiceFeignClient;
    @Mock
    private PaymentServiceFeignClient paymentServiceFeignClient;

    @InjectMocks
    private ProcessOrderUseCase processOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessOrderSuccessfullyWhenStockAvailableAndPaymentSuccessful() {
        // Given
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        orderReceiverDTO.setUser(userDTO);

        OrderItemDTO item1 = new OrderItemDTO();
        item1.setSku("SKU001");
        item1.setQuantity(2);

        OrderItemDTO item2 = new OrderItemDTO();
        item2.setSku("SKU002");
        item2.setQuantity(1);
        orderReceiverDTO.setItems(Arrays.asList(item1, item2));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setCardNumber("1234-5678-9012-3456");
        orderReceiverDTO.setPayment(paymentDTO);

        // Mock Product Service
        Product product1 = new Product();
        product1.setId(10L);
        product1.setPrice(BigDecimal.valueOf(50.00));
        Product product2 = new Product();
        product2.setId(20L);
        product2.setPrice(BigDecimal.valueOf(100.00));

        when(productServiceFeignClient.findBySku("SKU001")).thenReturn(product1);
        when(productServiceFeignClient.findBySku("SKU002")).thenReturn(product2);
        when(productServiceFeignClient.findById(10L)).thenReturn(product1);
        when(productServiceFeignClient.findById(20L)).thenReturn(product2);

        // Mock Stock Service
        StockDto stock1 = new StockDto();
        stock1.setId(100L);
        stock1.setProductId(10L);
        stock1.setQuantity(10);
        StockDto stock2 = new StockDto();
        stock2.setId(200L);
        stock2.setProductId(20L);
        stock2.setQuantity(5);

        when(stockServiceFeignClient.findByProductId(10L)).thenReturn(stock1);
        when(stockServiceFeignClient.findByProductId(20L)).thenReturn(stock2);

        // Mock Create Use Case
        Order savedOrder = new Order();
        savedOrder.setId(1L);
        when(createUseCase.execute(any(Order.class))).thenReturn(savedOrder);

        // Mock Payment Service
        PaymentResponse paymentResponse = new PaymentResponse(500L, "transaction123", PaymentStatus.SUCCESS.name());
        when(paymentServiceFeignClient.processPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

        // When
        processOrderUseCase.execute(orderReceiverDTO);

        // Then
        verify(productServiceFeignClient, times(1)).findBySku("SKU001");
        verify(productServiceFeignClient, times(1)).findBySku("SKU002");
        verify(stockServiceFeignClient, times(2)).findByProductId(10L);
        verify(stockServiceFeignClient, times(2)).findByProductId(20L);
        verify(stockServiceFeignClient, times(1)).update(eq(100L), any(UpdateStockParameter.class)); // For item1 decrement
        verify(stockServiceFeignClient, times(1)).update(eq(200L), any(UpdateStockParameter.class)); // For item2 decrement
        verify(productServiceFeignClient, times(1)).findById(10L);
        verify(productServiceFeignClient, times(1)).findById(20L);
        verify(paymentServiceFeignClient, times(1)).processPayment(any(PaymentRequest.class));
        verify(createUseCase, times(2)).execute(any(Order.class)); // Initial save and final save with paymentId
    }

    @Test
    void shouldCloseOrderWhenInsufficientStock() {
        // Given
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        orderReceiverDTO.setUser(userDTO);

        OrderItemDTO item1 = new OrderItemDTO();
        item1.setSku("SKU001");
        item1.setQuantity(2);
        orderReceiverDTO.setItems(Collections.singletonList(item1));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setCardNumber("1234-5678-9012-3456");
        orderReceiverDTO.setPayment(paymentDTO);

        // Mock Product Service
        Product product1 = new Product();
        product1.setId(10L);
        product1.setPrice(BigDecimal.valueOf(50.00));
        when(productServiceFeignClient.findBySku("SKU001")).thenReturn(product1);

        // Mock Stock Service - Insufficient stock
        StockDto stock1 = new StockDto();
        stock1.setId(100L);
        stock1.setProductId(10L);
        stock1.setQuantity(1); // Only 1 available, but 2 requested
        when(stockServiceFeignClient.findByProductId(10L)).thenReturn(stock1);

        // Mock Create Use Case
        Order savedOrder = new Order();
        savedOrder.setId(1L);
        when(createUseCase.execute(any(Order.class))).thenReturn(savedOrder);

        // When
        processOrderUseCase.execute(orderReceiverDTO);

        // Then
        verify(productServiceFeignClient, times(1)).findBySku("SKU001");
        verify(stockServiceFeignClient, times(1)).findByProductId(10L);
        verify(stockServiceFeignClient, never()).update(anyLong(), any(UpdateStockParameter.class)); // Stock should not be decremented
        verify(paymentServiceFeignClient, never()).processPayment(any(PaymentRequest.class)); // Payment should not be processed
        verify(createUseCase, times(1)).execute(argThat(order ->
                order.getStatus() == OrderStatus.FECHADO_SEM_ESTOQUE &&
                        order.getTotal().compareTo(BigDecimal.ZERO) == 0
        ));
    }
}

