package challenge.tech.dto.parameter;

import challenge.tech.domain.UserAuth;
import challenge.tech.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterUserParameterTest {

    @Test
    @DisplayName("Deve converter para domínio corretamente")
    void toDomain() {
        RegisterUserParameter param = new RegisterUserParameter("user@email.com", "11999999999", "user@email.com", "senha123", "12345678901");
        UserAuth domain = param.toDomain();
        assertEquals("user@email.com", domain.getEmail());
        assertEquals("senha123", domain.getPassword());
        assertEquals("12345678901", domain.getCpf());
        assertEquals(Set.of(UserRole.USER), domain.getRoles());
    }

    @Test
    @DisplayName("Deve retornar o e-mail corretamente")
    void getEmail() {
        RegisterUserParameter param = new RegisterUserParameter("user@email.com", "11999999999", "user@email.com", "senha123", "12345678901");
        assertEquals("user@email.com", param.getEmail());
    }

    @Test
    @DisplayName("Deve retornar a senha corretamente")
    void getPassword() {
        RegisterUserParameter param = new RegisterUserParameter("user@email.com", "11999999999", "user@email.com", "senha123", "12345678901");
        assertEquals("senha123", param.getPassword());
    }

    @Test
    @DisplayName("Deve retornar o CPF corretamente")
    void getCpf() {
        RegisterUserParameter param = new RegisterUserParameter("user@email.com", "11999999999", "user@email.com", "senha123", "12345678901");
        assertEquals("12345678901", param.getCpf());
    }
}