package challenge.tech.usecase;

import challenge.tech.domain.Order;
import challenge.tech.gateway.database.OrderJpaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UpdatePaymentUseCaseTest {

    @Mock
    private OrderJpaGateway orderJpaGateway;

    @InjectMocks
    private UpdatePaymentUseCase updatePaymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdatePaymentSuccessfully() {
        // Given
        Order order = new Order(); // You might want to set some properties for a more realistic test
        when(orderJpaGateway.save(order)).thenReturn(order);

        // When
        Order updatedOrder = updatePaymentUseCase.execute(order);

        // Then
        assertEquals(order, updatedOrder);
        verify(orderJpaGateway, times(1)).save(order);
    }
}
