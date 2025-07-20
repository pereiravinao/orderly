package challenge.tech.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        address.setId(id);
        assertEquals(id, address.getId());
    }

    @Test
    void testSetAndGetStreet() {
        String street = "Rua Teste";
        address.setStreet(street);
        assertEquals(street, address.getStreet());
    }

    @Test
    void testSetAndGetNumber() {
        String number = "123";
        address.setNumber(number);
        assertEquals(number, address.getNumber());
    }

    @Test
    void testSetAndGetComplement() {
        String complement = "Apto 101";
        address.setComplement(complement);
        assertEquals(complement, address.getComplement());
    }

    @Test
    void testSetAndGetCity() {
        String city = "Cidade Teste";
        address.setCity(city);
        assertEquals(city, address.getCity());
    }

    @Test
    void testSetAndGetState() {
        String state = "TS";
        address.setState(state);
        assertEquals(state, address.getState());
    }

    @Test
    void testSetAndGetZipCode() {
        String zipCode = "12345-678";
        address.setZipCode(zipCode);
        assertEquals(zipCode, address.getZipCode());
    }

    @Test
    void testSetAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        address.setCreatedAt(now);
        assertEquals(now, address.getCreatedAt());
    }

    @Test
    void testSetAndGetUser() {
        User user = new User();
        user.setId(1L);
        address.setUser(user);
        assertEquals(user, address.getUser());
    }
}
