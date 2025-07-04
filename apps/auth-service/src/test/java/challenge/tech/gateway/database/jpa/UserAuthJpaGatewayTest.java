package challenge.tech.gateway.database.jpa;

import challenge.tech.domain.UserAuth;
import challenge.tech.enums.UserRole;
import challenge.tech.gateway.database.jpa.entity.UserAuthEntity;
import challenge.tech.gateway.database.jpa.repository.UserAuthRepository;
import challenge.tech.exceptions.auth.AuthExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserAuthJpaGatewayTest {

    @Mock
    private UserAuthRepository userAuthRepository;

    @InjectMocks
    private UserAuthJpaGateway userAuthJpaGateway;

    private UserAuth userAuth;
    private UserAuthEntity userAuthEntity;

    @BeforeEach
    void setup() {
        userAuth = UserAuth.builder()
                .id(1L)
                .email("user@email.com")
                .cpf("12345678901")
                .password("senha123")
                .roles(Set.of(UserRole.USER))
                .build();
        userAuthEntity = new UserAuthEntity(userAuth);
    }

    @Test
    @DisplayName("Salva um usuário e retorna corretamente")
    void shouldSaveUserAndReturnDomain() {
        when(userAuthRepository.save(any(UserAuthEntity.class))).thenReturn(userAuthEntity);
        var saved = userAuthJpaGateway.save(userAuth);
        assertThat(saved)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(userAuth);
        verify(userAuthRepository).save(any(UserAuthEntity.class));
    }

    @Test
    @DisplayName("Busca usuário por e-mail com sucesso")
    void shouldFindUserByEmail() {
        when(userAuthRepository.findByEmail(userAuth.getEmail())).thenReturn(Optional.of(userAuthEntity));
        var found = userAuthJpaGateway.findByEmail(userAuth.getEmail());
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(userAuth.getEmail());
        verify(userAuthRepository).findByEmail(userAuth.getEmail());
    }

    @Test
    @DisplayName("Lança exceção ao não encontrar usuário por e-mail")
    void shouldThrowExceptionWhenUserNotFound() {
        String emailInexistente = "naoexiste@email.com";
        when(userAuthRepository.findByEmail(emailInexistente)).thenReturn(Optional.empty());
        var exception = assertThrows(
                AuthExceptionHandler.class,
                () -> userAuthJpaGateway.findByEmail(emailInexistente)
        );
        assertThat(exception.getMessage()).isEqualTo(AuthExceptionHandler.userNotFound().getMessage());
        verify(userAuthRepository).findByEmail(emailInexistente);
    }

    @Test
    @DisplayName("Verifica existência por e-mail ou cpf")
    void shouldCheckExistsByEmailOrCpf() {
        when(userAuthRepository.existsByEmailOrCpf(userAuth.getEmail(), userAuth.getCpf())).thenReturn(true);
        boolean exists = userAuthJpaGateway.existsByEmailOrCpf(userAuth.getEmail(), userAuth.getCpf());
        assertThat(exists).isTrue();
        verify(userAuthRepository).existsByEmailOrCpf(userAuth.getEmail(), userAuth.getCpf());
    }
}