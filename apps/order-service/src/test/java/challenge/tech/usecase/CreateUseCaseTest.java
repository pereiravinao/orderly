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

class CreateUseCaseTest {

    @Mock
    private OrderJpaGateway orderJpaGateway;

    @InjectMocks
    private CreateUseCase createUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        Order order = new Order(); // You might want to set some properties for a more realistic test
        when(orderJpaGateway.save(order)).thenReturn(order);

        // When
        Order createdOrder = createUseCase.execute(order);

        // Then
        assertEquals(order, createdOrder);
        verify(orderJpaGateway, times(1)).save(order);
    }
}
