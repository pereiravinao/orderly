package challenge.tech.usecase;

import challenge.tech.gateway.database.OrderJpaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DeleteUseCaseTest {

    @Mock
    private OrderJpaGateway orderJpaGateway;

    @InjectMocks
    private DeleteUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteOrderSuccessfully() {
        // Given
        Long orderId = 1L;

        // When
        deleteUseCase.execute(orderId);

        // Then
        verify(orderJpaGateway, times(1)).deleteById(orderId);
    }
}
