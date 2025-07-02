package challange.tech.dto.parameter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginParameterTest {

    @Test
    @DisplayName("Deve retornar o e-mail corretamente")
    void getEmail() {
        LoginParameter param = new LoginParameter("user@email.com", "senha123");
        assertEquals("user@email.com", param.getEmail());
    }

    @Test
    @DisplayName("Deve retornar a senha corretamente")
    void getPassword() {
        LoginParameter param = new LoginParameter("user@email.com", "senha123");
        assertEquals("senha123", param.getPassword());
    }
}