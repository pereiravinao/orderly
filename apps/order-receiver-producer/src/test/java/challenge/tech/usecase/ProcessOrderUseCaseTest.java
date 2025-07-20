package challenge.tech.usecase;

import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.dto.UserAuthDTO;
import challenge.tech.dto.UserDTO;
import challenge.tech.gateway.producer.OrderReceiverProducer;
import challenge.tech.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProcessOrderUseCaseTest {

    @Mock
    private OrderReceiverProducer orderReceiverProducer;

    @InjectMocks
    private ProcessOrderUseCase processOrderUseCase;

    private UserAuthDTO currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentUser = UserAuthDTO.builder().id(1L).build();
    }

    @Test
    void shouldProcessOrderSuccessfully() {
        // Given
        OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);

            // When
            processOrderUseCase.execute(orderReceiverDTO);

            // Then
            verify(orderReceiverProducer, times(1)).sendOrder(argThat(dto ->
                    dto.getUser() != null && dto.getUser().getId().equals(currentUser.getId())
            ));
        }
    }
}
