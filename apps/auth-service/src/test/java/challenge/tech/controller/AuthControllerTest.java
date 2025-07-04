package challenge.tech.controller;

import challenge.tech.domain.UserAuth;
import challenge.tech.dto.parameter.LoginParameter;
import challenge.tech.dto.parameter.RegisterUserParameter;
import challenge.tech.dto.presenter.LoginResponse;
import challenge.tech.dto.presenter.RegisterUserPresenter;
import challenge.tech.usecase.LoginUseCase;
import challenge.tech.usecase.RegisterUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private RegisterUseCase registerUseCase;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve realizar login e retornar o token corretamente")
    void login_shouldReturnToken() {
        LoginParameter loginParameter = new LoginParameter();
        var userAuth = mock(UserAuth.class);
        when(userAuth.getToken()).thenReturn("token123");
        when(loginUseCase.execute(any(LoginParameter.class))).thenReturn(userAuth);

        ResponseEntity<LoginResponse> responseEntity = authController.login(loginParameter);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals("token123", responseEntity.getBody().getToken());
    }

    @Test
    @DisplayName("Deve registrar usuário e retornar os dados corretamente")
    void register_shouldReturnUserData() {
        RegisterUserParameter registerParameter = new RegisterUserParameter("user@email.com", "11999999999", "user@email.com", "senha123", "12345678900");
        var userAuth = mock(UserAuth.class);
        when(userAuth.getId()).thenReturn(1L);
        when(userAuth.getEmail()).thenReturn("user@email.com");
        when(userAuth.getToken()).thenReturn("token123");
        when(registerUseCase.execute(any())).thenReturn(userAuth);

        ResponseEntity<RegisterUserPresenter> responseEntity = authController.register(registerParameter);

        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().getId());
        assertEquals("user@email.com", responseEntity.getBody().getEmail());
        assertEquals("token123", responseEntity.getBody().getToken());
    }
}
