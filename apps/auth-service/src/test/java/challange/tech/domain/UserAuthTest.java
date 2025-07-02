package challange.tech.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthTest {
    private UserAuth userAuth;

    @BeforeEach
    void setUp() {
        userAuth = UserAuth.builder()
                .id(1L)
                .email("test@email.com")
                .password("password123")
                .cpf("12345678901")
                .createdAt(LocalDateTime.now())
                .roles(Set.of(challange.tech.enums.UserRole.ROLE_USER))
                .token("token123")
                .build();
    }

    @AfterEach
    void tearDown() {
        userAuth = null;
    }

    @Test
    @DisplayName("Deve retornar o id corretamente")
    void getId_shouldReturnId() {
        assertEquals(1L, userAuth.getId());
    }

    @Test
    @DisplayName("Deve retornar o email corretamente")
    void getEmail_shouldReturnEmail() {
        assertEquals("test@email.com", userAuth.getEmail());
    }

    @Test
    @DisplayName("Deve retornar a senha corretamente")
    void getPassword_shouldReturnPassword() {
        assertEquals("password123", userAuth.getPassword());
    }

    @Test
    @DisplayName("Deve retornar o CPF corretamente")
    void getCpf_shouldReturnCpf() {
        assertEquals("12345678901", userAuth.getCpf());
    }

    @Test
    @DisplayName("Deve retornar a data de criação corretamente")
    void getCreatedAt_shouldReturnCreatedAt() {
        assertNotNull(userAuth.getCreatedAt());
    }

    @Test
    @DisplayName("Deve retornar as roles corretamente")
    void getRoles_shouldReturnRoles() {
        assertTrue(userAuth.getRoles().contains(challange.tech.enums.UserRole.ROLE_USER));
    }

    @Test
    @DisplayName("Deve retornar o token corretamente")
    void getToken_shouldReturnToken() {
        assertEquals("token123", userAuth.getToken());
    }

    @Test
    @DisplayName("Deve construir um objeto UserAuth corretamente com o builder")
    void builder_shouldBuildUserAuthCorrectly() {
        UserAuth user = UserAuth.builder()
                .id(2L)
                .email("builder@email.com")
                .password("builderPass")
                .cpf("98765432100")
                .createdAt(LocalDateTime.of(2023, 1, 1, 12, 0))
                .roles(Set.of(challange.tech.enums.UserRole.ROLE_ADMIN))
                .token("builderToken")
                .build();

        assertEquals(2L, user.getId());
        assertEquals("builder@email.com", user.getEmail());
        assertEquals("builderPass", user.getPassword());
        assertEquals("98765432100", user.getCpf());
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), user.getCreatedAt());
        assertTrue(user.getRoles().contains(challange.tech.enums.UserRole.ROLE_ADMIN));
        assertEquals("builderToken", user.getToken());
    }
}