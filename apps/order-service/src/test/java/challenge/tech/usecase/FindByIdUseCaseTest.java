package challenge.tech.usecase;

import challenge.tech.domain.Order;
import challenge.tech.dto.UserAuthDTO;
import challenge.tech.gateway.database.OrderJpaGateway;
import challenge.tech.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindByIdUseCaseTest {

    @Mock
    private OrderJpaGateway orderJpaGateway;

    @InjectMocks
    private FindByIdUseCase findByIdUseCase;

    private UserAuthDTO currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentUser = UserAuthDTO.builder().id(1L).build(); // Current user ID
    }

    @Test
    void shouldReturnOrderWhenFoundAndOwnedByUser() {
        // Given
        Long orderId = 10L;
        Order order = new Order();
        order.setUserId(currentUser.getId()); // Order owned by current user

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);
            when(orderJpaGateway.findById(orderId)).thenReturn(order);

            // When
            Order result = findByIdUseCase.execute(orderId);

            // Then
            assertNotNull(result);
            assertEquals(order, result);
            // findById is called twice: once for the check, once for the return
            verify(orderJpaGateway, times(2)).findById(orderId);
        }
    }

    @Test
    void shouldThrowExceptionWhenOrderNotOwnedByUser() {
        // Given
        Long orderId = 10L;
        Order order = new Order();
        order.setUserId(2L); // Order owned by a different user

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);
            when(orderJpaGateway.findById(orderId)).thenReturn(order);

            // When & Then
            // Assuming OrderExceptionHandler.notOwnedByUser() returns a RuntimeException or a subclass
            // and its message contains "Order not owned by user".
            RuntimeException exception = assertThrows(RuntimeException.class, () -> findByIdUseCase.execute(orderId));
            assertTrue(exception.getMessage().contains("Você não possui permissão para acessar esse pedido."));

            // findById is called once before the exception is thrown
            verify(orderJpaGateway, times(1)).findById(orderId);
        }
    }

    @Test
    void shouldThrowNullPointerExceptionWhenOrderNotFound() {
        // Given
        Long orderId = 10L;

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);
            when(orderJpaGateway.findById(orderId)).thenReturn(null); // Simulate order not found

            // When & Then
            // The code `orderJpaGateway.findById(id).getUserId()` will throw NullPointerException if findById returns null.
            assertThrows(NullPointerException.class, () -> findByIdUseCase.execute(orderId));

            // findById is called once
            verify(orderJpaGateway, times(1)).findById(orderId);
        }
    }
}
