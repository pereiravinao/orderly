package challenge.tech.usecase;

import challenge.tech.domain.UserAuth;
import challenge.tech.enums.UserRole;
import challenge.tech.gateway.database.jpa.UserAuthJpaGateway;
import challenge.tech.services.JwtTokenService;
import challenge.tech.client.UserClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

class RegisterUseCaseTest {
    @Mock
    private UserAuthJpaGateway userAuthJpaGateway;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private RegisterUseCase registerUseCase;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Deve registrar usuário com sucesso")
    void shouldRegisterUserSuccessfully() {
        UserAuth user = new UserAuth();
        user.setEmail("test@email.com");
        user.setCpf("12345678900");
        user.setPassword("plainPassword");
        when(userAuthJpaGateway.existsByEmailOrCpf(anyString(), anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userAuthJpaGateway.save(any(UserAuth.class))).thenAnswer(invocation -> {
            UserAuth entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });
        when(jwtTokenService.generateToken(any())).thenReturn("token123");
        doNothing().when(userClient).createUser(any());

        UserAuth result = registerUseCase.execute(user);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getRoles().contains(UserRole.USER));
        assertEquals("token123", result.getToken());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar registrar usuário já existente")
    void shouldThrowExceptionWhenUserAlreadyExists() {
        UserAuth user = new UserAuth();
        user.setEmail("test@email.com");
        user.setCpf("12345678900");
        when(userAuthJpaGateway.existsByEmailOrCpf(anyString(), anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> registerUseCase.execute(user));
    }
}