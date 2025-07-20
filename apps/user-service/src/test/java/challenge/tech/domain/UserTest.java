package challenge.tech.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    void testSetAndGetAuthId() {
        Long authId = 101L;
        user.setAuthId(authId);
        assertEquals(authId, user.getAuthId());
    }

    @Test
    void testSetAndGetName() {
        String name = "Test User";
        user.setName(name);
        assertEquals(name, user.getName());
    }

    @Test
    void testSetAndGetEmail() {
        String email = "test@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSetAndGetCpf() {
        String cpf = "123.456.789-00";
        user.setCpf(cpf);
        assertEquals(cpf, user.getCpf());
    }

    @Test
    void testSetAndGetPhone() {
        String phone = "(11) 98765-4321";
        user.setPhone(phone);
        assertEquals(phone, user.getPhone());
    }

    @Test
    void testSetAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void testSetAndGetAddresses() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        user.setAddresses(addresses);
        assertEquals(addresses, user.getAddresses());
    }

    @Test
    void testUpdateMethodWithNameAndPhone() {
        User updateData = new User();
        updateData.setName("Updated Name");
        updateData.setPhone("(22) 12345-6789");

        user.setName("Original Name");
        user.setPhone("(11) 11111-1111");
        user.update(updateData);

        assertEquals("Updated Name", user.getName());
        assertEquals("(22) 12345-6789", user.getPhone());
    }

    @Test
    void testUpdateMethodWithNullUpdateData() {
        user.setName("Original Name");
        user.setPhone("(11) 11111-1111");
        user.update(null);

        assertEquals("Original Name", user.getName());
        assertEquals("(11) 11111-1111", user.getPhone());
    }

    @Test
    void testUpdateMethodWithNullNameAndPhone() {
        User updateData = new User();
        updateData.setName(null);
        updateData.setPhone(null);

        user.setName("Original Name");
        user.setPhone("(11) 11111-1111");
        user.update(updateData);

        assertEquals("Original Name", user.getName());
        assertEquals("(11) 11111-1111", user.getPhone());
    }
}
