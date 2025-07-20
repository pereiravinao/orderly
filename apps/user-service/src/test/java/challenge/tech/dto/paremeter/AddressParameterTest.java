package challenge.tech.dto.paremeter;

import challenge.tech.domain.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddressParameterTest {

    private AddressParameter addressParameter;

    @BeforeEach
    void setUp() {
        addressParameter = new AddressParameter();
    }

    @Test
    void testSetAndGetStreet() {
        String street = "Rua Teste";
        addressParameter.setStreet(street);
        assertEquals(street, addressParameter.getStreet());
    }

    @Test
    void testSetAndGetNumber() {
        String number = "123";
        addressParameter.setNumber(number);
        assertEquals(number, addressParameter.getNumber());
    }

    @Test
    void testSetAndGetComplement() {
        String complement = "Apto 101";
        addressParameter.setComplement(complement);
        assertEquals(complement, addressParameter.getComplement());
    }

    @Test
    void testSetAndGetCity() {
        String city = "Cidade Teste";
        addressParameter.setCity(city);
        assertEquals(city, addressParameter.getCity());
    }

    @Test
    void testSetAndGetState() {
        String state = "TS";
        addressParameter.setState(state);
        assertEquals(state, addressParameter.getState());
    }

    @Test
    void testSetAndGetZipCode() {
        String zipCode = "12345-678";
        addressParameter.setZipCode(zipCode);
        assertEquals(zipCode, addressParameter.getZipCode());
    }

    @Test
    void toDomainShouldMapAddressParameterToAddress() {
        addressParameter.setStreet("Rua Teste");
        addressParameter.setNumber("123");
        addressParameter.setComplement("Apto 101");
        addressParameter.setCity("Cidade Teste");
        addressParameter.setState("TS");
        addressParameter.setZipCode("12345-678");

        Address address = addressParameter.toDomain();

        assertNotNull(address);
        assertEquals("Rua Teste", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("Apto 101", address.getComplement());
        assertEquals("Cidade Teste", address.getCity());
        assertEquals("TS", address.getState());
        assertEquals("12345-678", address.getZipCode());
    }
}
