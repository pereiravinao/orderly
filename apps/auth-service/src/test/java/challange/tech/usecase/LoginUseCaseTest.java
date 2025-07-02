package challange.tech.usecase;

import challange.tech.domain.UserAuth;
import challange.tech.dto.UserAuthDTO;
import challange.tech.dto.parameter.LoginParameter;
import challange.tech.exceptions.auth.AuthExceptionHandler;
import challange.tech.gateway.database.jpa.UserAuthJpaGateway;
import challange.tech.services.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class LoginUseCaseTest {

    private UserAuthJpaGateway userAuthJpaGateway;
    private JwtTokenService jwtTokenService;
    private PasswordEncoder passwordEncoder;
    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        userAuthJpaGateway = mock(UserAuthJpaGateway.class);
        jwtTokenService = mock(JwtTokenService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        loginUseCase = new LoginUseCase(userAuthJpaGateway, jwtTokenService, passwordEncoder);
    }

    @Test
    @DisplayName("Deve retornar um token válido quando o login for bem-sucedido")
    void shouldReturnAValidTokenWhenLoginIsSuccessful() {
        var loginParam = new LoginParameter("user@email.com", "senha123");
        var userAuth = new UserAuth();
        userAuth.setId(1L);
        userAuth.setEmail("user@email.com");
        userAuth.setPassword("senhaCriptografada");

        when(userAuthJpaGateway.findByEmail("user@email.com")).thenReturn(userAuth);
        when(passwordEncoder.matches("senha123", "senhaCriptografada")).thenReturn(true);
        when(jwtTokenService.generateToken(any(UserAuthDTO.class))).thenReturn("token-jwt");

        var result = loginUseCase.execute(loginParam);

        assertNotNull(result);
        assertEquals("token-jwt", result.getToken());
        verify(jwtTokenService).generateToken(any(UserAuthDTO.class));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando a senha for inválida")
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        var loginParam = new LoginParameter("user@email.com", "senhaErrada");
        var userAuth = new UserAuth();
        userAuth.setId(1L);
        userAuth.setEmail("user@email.com");
        userAuth.setPassword("senhaCriptografada");

        when(userAuthJpaGateway.findByEmail("user@email.com")).thenReturn(userAuth);
        when(passwordEncoder.matches("senhaErrada", "senhaCriptografada")).thenReturn(false);

        assertThrows(AuthExceptionHandler.class, () -> loginUseCase.execute(loginParam));
    }
}