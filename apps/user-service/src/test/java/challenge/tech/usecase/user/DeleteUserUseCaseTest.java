package challenge.tech.usecase.user;

import challenge.tech.gateway.database.UserJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @Mock
    private UserJpaGateway userJpaGateway;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    void shouldCallDeleteByIdOnGateway() {
        Long userId = 1L;
        doNothing().when(userJpaGateway).deleteById(anyLong());

        deleteUserUseCase.execute(userId);

        verify(userJpaGateway, times(1)).deleteById(userId);
    }
}
