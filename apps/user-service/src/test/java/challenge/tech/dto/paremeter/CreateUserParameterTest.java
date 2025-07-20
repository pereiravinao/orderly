package challenge.tech.dto.paremeter;

import challenge.tech.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateUserParameterTest {

    private CreateUserParameter createUserParameter;

    @BeforeEach
    void setUp() {
        createUserParameter = new CreateUserParameter(101L, "Test User", "(11) 98765-4321", "test@example.com", "123.456.789-00");
    }

    @Test
    void testGetAuthId() {
        assertEquals(101L, createUserParameter.getAuthId());
    }

    @Test
    void testGetName() {
        assertEquals("Test User", createUserParameter.getName());
    }

    @Test
    void testGetPhone() {
        assertEquals("(11) 98765-4321", createUserParameter.getPhone());
    }

    @Test
    void testGetEmail() {
        assertEquals("test@example.com", createUserParameter.getEmail());
    }

    @Test
    void testGetCpf() {
        assertEquals("123.456.789-00", createUserParameter.getCpf());
    }

    @Test
    void toDomainShouldMapCreateUserParameterToUser() {
        User user = createUserParameter.toDomain();

        assertNotNull(user);
        assertEquals(101L, user.getAuthId());
        assertEquals("Test User", user.getName());
        assertEquals("(11) 98765-4321", user.getPhone());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("123.456.789-00", user.getCpf());
    }
}
