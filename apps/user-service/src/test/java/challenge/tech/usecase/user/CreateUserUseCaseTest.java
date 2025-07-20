package challenge.tech.usecase.user;

import challenge.tech.domain.User;
import challenge.tech.exceptions.user.UserExceptionHandler;
import challenge.tech.gateway.database.UserJpaGateway;
import challenge.tech.utils.SecurityUtils;
import challenge.tech.dto.UserAuthDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserJpaGateway userJpaGateway;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setCpf("123.456.789-00");
    }

    @Test
    void shouldCreateUserSuccessfullyWhenAuthIdIsProvided() {
        user.setAuthId(1L);
        when(userJpaGateway.existsByEmailOrCpf(anyString(), anyString())).thenReturn(false);
        when(userJpaGateway.save(any(User.class))).thenReturn(user);

        User result = createUserUseCase.execute(user);

        assertNotNull(result);
        assertEquals(1L, result.getAuthId());
        verify(userJpaGateway, times(1)).existsByEmailOrCpf(anyString(), anyString());
        verify(userJpaGateway, times(1)).save(any(User.class));
    }

    @Test
    void shouldCreateUserSuccessfullyWhenAuthIdIsNotProvided() {
        UserAuthDTO currentUser = UserAuthDTO.builder().id(2L).build();

        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);
            when(userJpaGateway.existsByEmailOrCpf(anyString(), anyString())).thenReturn(false);
            when(userJpaGateway.save(any(User.class))).thenReturn(user);

            User result = createUserUseCase.execute(user);

            assertNotNull(result);
            assertEquals(2L, result.getAuthId());
            verify(userJpaGateway, times(1)).existsByEmailOrCpf(anyString(), anyString());
            verify(userJpaGateway, times(1)).save(any(User.class));
        }
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        when(userJpaGateway.existsByEmailOrCpf(anyString(), anyString())).thenReturn(true);

        assertThrows(UserExceptionHandler.class, () -> createUserUseCase.execute(user));

        verify(userJpaGateway, times(1)).existsByEmailOrCpf(anyString(), anyString());
        verify(userJpaGateway, never()).save(any(User.class));
    }
}
