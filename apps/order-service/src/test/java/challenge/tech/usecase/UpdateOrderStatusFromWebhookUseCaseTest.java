package challenge.tech.usecase;

import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.client.dto.StockDto;
import challenge.tech.client.dto.UpdateStockParameter;
import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.enums.OrderStatus;
import challenge.tech.enums.PaymentStatus;
import challenge.tech.gateway.database.OrderJpaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UpdateOrderStatusFromWebhookUseCaseTest {

    @Mock
    private OrderJpaGateway orderJpaGateway;
    @Mock
    private StockServiceFeignClient stockServiceFeignClient;

    @InjectMocks
    private UpdateOrderStatusFromWebhookUseCase useCase;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUserId(10L);
        testOrder.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        testOrder.setTotal(BigDecimal.valueOf(100.00));

        OrderItem item = new OrderItem();
        item.setProductId(100L);
        item.setQuantity(2);
        testOrder.setProducts(Collections.singletonList(item));
    }

    @Test
    void shouldUpdateStatusToSuccessAndNotIncrementStockWhenPaymentIsSuccess() {
        // Given
        when(orderJpaGateway.findById(testOrder.getId())).thenReturn(testOrder);

        // When
        useCase.execute(testOrder.getId(), PaymentStatus.SUCCESS);

        // Then
        verify(orderJpaGateway, times(1)).findById(testOrder.getId());
        verify(stockServiceFeignClient, never()).update(anyLong(), any(UpdateStockParameter.class));
        verify(orderJpaGateway, times(1)).save(argThat(order ->
                order.getStatus().equals(OrderStatus.FECHADO_COM_SUCESSO)
        ));
    }

    @Test
    void shouldUpdateStatusToFailedAndIncrementStockWhenPaymentIsFailed() {
        // Given
        when(orderJpaGateway.findById(testOrder.getId())).thenReturn(testOrder);

        StockDto stockDto = new StockDto();
        stockDto.setId(200L);
        stockDto.setProductId(100L);
        stockDto.setQuantity(5);
        when(stockServiceFeignClient.findByProductId(100L)).thenReturn(stockDto);

        // When
        useCase.execute(testOrder.getId(), PaymentStatus.FAILED);

        // Then
        verify(orderJpaGateway, times(1)).findById(testOrder.getId());
        verify(stockServiceFeignClient, times(1)).findByProductId(100L);
        verify(stockServiceFeignClient, times(1)).update(eq(200L), argThat(param ->
                param.getProductId().equals(100L) && param.getQuantity().equals(2)
        ));
        verify(orderJpaGateway, times(1)).save(argThat(order ->
                order.getStatus().equals(OrderStatus.FECHADO_SEM_CREDITO)
        ));
    }

    @Test
    void shouldDoNothingWhenOrderNotFound() {
        // Given
        when(orderJpaGateway.findById(anyLong())).thenReturn(null);

        // When
        useCase.execute(99L, PaymentStatus.SUCCESS);

        // Then
        verify(orderJpaGateway, times(1)).findById(99L);
        verify(stockServiceFeignClient, never()).update(anyLong(), any(UpdateStockParameter.class));
        verify(orderJpaGateway, never()).save(any(Order.class));
    }

    @Test
    void shouldMapUnknownPaymentStatusToAguardandoPagamento() {
        // Given
        when(orderJpaGateway.findById(testOrder.getId())).thenReturn(testOrder);

        // When
        useCase.execute(testOrder.getId(), PaymentStatus.PENDING); // Assuming PENDING is not explicitly handled

        // Then
        verify(orderJpaGateway, times(1)).findById(testOrder.getId());
        verify(stockServiceFeignClient, never()).update(anyLong(), any(UpdateStockParameter.class));
        verify(orderJpaGateway, times(1)).save(argThat(order ->
                order.getStatus().equals(OrderStatus.AGUARDANDO_PAGAMENTO)
        ));
    }
}
