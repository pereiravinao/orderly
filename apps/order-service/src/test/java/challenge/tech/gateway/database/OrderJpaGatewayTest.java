package challenge.tech.gateway.database;

import challenge.tech.domain.Order;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.OrderEntity;
import challenge.tech.gateway.database.jpa.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderJpaGatewayTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderJpaGateway orderJpaGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllOrdersByUserId() {
        // Given
        Long userId = 1L;
        Pageable pageable = mock(Pageable.class);
        OrderEntity orderEntity = mock(OrderEntity.class);
        Order orderDomain = new Order();
        when(orderEntity.toDomain()).thenReturn(orderDomain);
        Page<OrderEntity> pageOfEntities = new PageImpl<>(Collections.singletonList(orderEntity));
        when(orderRepository.findAllByUserId(userId, pageable)).thenReturn(pageOfEntities);

        // When
        Page<Order> result = orderJpaGateway.findAll(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(orderDomain, result.getContent().get(0));
        verify(orderRepository, times(1)).findAllByUserId(userId, pageable);
        verify(orderEntity, times(1)).toDomain();
    }

    @Test
    void shouldFindOrderByIdWhenExists() {
        // Given
        Long orderId = 1L;
        OrderEntity orderEntity = mock(OrderEntity.class);
        Order orderDomain = new Order();
        when(orderEntity.toDomain()).thenReturn(orderDomain);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        // When
        Order result = orderJpaGateway.findById(orderId);

        // Then
        assertNotNull(result);
        assertEquals(orderDomain, result);
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderEntity, times(1)).toDomain();
    }

    @Test
    void shouldThrowExceptionWhenOrderByIdNotFound() {
        // Given
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(StockExceptionHandler.class, () -> orderJpaGateway.findById(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void shouldSaveOrderSuccessfully() {
        // Given
        Order orderDomain = new Order();
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderEntity.toDomain()).thenReturn(orderDomain);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        // When
        Order result = orderJpaGateway.save(orderDomain);

        // Then
        assertNotNull(result);
        assertEquals(orderDomain, result);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderEntity, times(1)).toDomain();
    }

    @Test
    void shouldDeleteOrderById() {
        // Given
        Long orderId = 1L;

        // When
        orderJpaGateway.deleteById(orderId);

        // Then
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
