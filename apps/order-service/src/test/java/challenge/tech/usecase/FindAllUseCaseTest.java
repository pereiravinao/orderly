package challenge.tech.usecase;

import challenge.tech.domain.Order;
import challenge.tech.dto.UserAuthDTO;
import challenge.tech.gateway.database.OrderJpaGateway;
import challenge.tech.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.mockito.MockedStatic;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAllUseCaseTest {

    @Mock
    private OrderJpaGateway orderJpaGateway;

    @InjectMocks
    private FindAllUseCase findAllUseCase;

    private UserAuthDTO currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentUser = UserAuthDTO.builder().id(1L).build();
    }

    @Test
    void shouldReturnAllOrdersForCurrentUser() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Page<Order> expectedPage = new PageImpl<>(Collections.singletonList(new Order()));
        Long currentUserId = currentUser.getId();

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);
            when(orderJpaGateway.findAll(currentUserId, pageable)).thenReturn(expectedPage);

            // When
            Page<Order> result = findAllUseCase.execute(pageable);

            // Then
            assertEquals(expectedPage, result);
            verify(orderJpaGateway, times(1)).findAll(currentUserId, pageable);
        }
    }
}
