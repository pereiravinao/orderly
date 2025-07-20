package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Address;
import challenge.tech.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AddressEntityTest {

    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        addressEntity = new AddressEntity();
    }

    @Test
    void onCreateShouldSetCreatedAtIfNull() {
        assertNull(addressEntity.getCreatedAt());
        addressEntity.onCreate();
        assertNotNull(addressEntity.getCreatedAt());
    }

    @Test
    void onCreateShouldNotChangeCreatedAtIfNotNull() {
        LocalDateTime initialTime = LocalDateTime.of(2023, 1, 1, 10, 0, 0);
        addressEntity.setCreatedAt(initialTime);
        addressEntity.onCreate();
        assertEquals(initialTime, addressEntity.getCreatedAt());
    }

    @Test
    void constructorShouldMapAddressToAddressEntity() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setNumber("123");
        address.setComplement("Apto 101");
        address.setCity("Test City");
        address.setState("TS");
        address.setZipCode("12345-678");
        address.setCreatedAt(LocalDateTime.now());

        User user = new User();
        user.setId(10L);
        address.setUser(user);

        AddressEntity newAddressEntity = new AddressEntity(address);

        assertEquals(address.getId(), newAddressEntity.getId());
        assertEquals(address.getStreet(), newAddressEntity.getStreet());
        assertEquals(address.getNumber(), newAddressEntity.getNumber());
        assertEquals(address.getComplement(), newAddressEntity.getComplement());
        assertEquals(address.getCity(), newAddressEntity.getCity());
        assertEquals(address.getState(), newAddressEntity.getState());
        assertEquals(address.getZipCode(), newAddressEntity.getZipCode());
        assertEquals(address.getCreatedAt(), newAddressEntity.getCreatedAt());
        assertNotNull(newAddressEntity.getUser());
        assertEquals(user.getId(), newAddressEntity.getUser().getId());
    }

    @Test
    void toDomainShouldMapAddressEntityToAddress() {
        addressEntity.setId(1L);
        addressEntity.setStreet("Test Street");
        addressEntity.setNumber("123");
        addressEntity.setComplement("Apto 101");
        addressEntity.setCity("Test City");
        addressEntity.setState("TS");
        addressEntity.setZipCode("12345-678");
        addressEntity.setCreatedAt(LocalDateTime.now());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10L);
        addressEntity.setUser(userEntity);

        Address domainAddress = addressEntity.toDomain();

        assertNotNull(domainAddress);
        assertEquals(addressEntity.getId(), domainAddress.getId());
        assertEquals(addressEntity.getStreet(), domainAddress.getStreet());
        assertEquals(addressEntity.getNumber(), domainAddress.getNumber());
        assertEquals(addressEntity.getComplement(), domainAddress.getComplement());
        assertEquals(addressEntity.getCity(), domainAddress.getCity());
        assertEquals(addressEntity.getState(), domainAddress.getState());
        assertEquals(addressEntity.getZipCode(), domainAddress.getZipCode());
        assertEquals(addressEntity.getCreatedAt(), domainAddress.getCreatedAt());
        // Note: The toDomain method in AddressEntity does not map the User object.
        // So, we don't assert on the user object here.
    }
}
