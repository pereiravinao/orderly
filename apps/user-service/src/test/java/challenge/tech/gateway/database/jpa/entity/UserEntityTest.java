package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Address;
import challenge.tech.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
    }

    @Test
    void prePersistShouldSetCreatedAtIfNull() {
        assertNull(userEntity.getCreatedAt());
        userEntity.prePersist();
        assertNotNull(userEntity.getCreatedAt());
    }

    @Test
    void prePersistShouldNotChangeCreatedAtIfNotNull() {
        LocalDateTime initialTime = LocalDateTime.of(2023, 1, 1, 10, 0, 0);
        userEntity.setCreatedAt(initialTime);
        userEntity.prePersist();
        assertEquals(initialTime, userEntity.getCreatedAt());
    }

    @Test
    void constructorShouldMapUserToUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setAuthId(101L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setCpf("123.456.789-00");
        user.setPhone("11987654321");

        Address address = new Address();
        address.setId(10L);
        user.setAddresses(Collections.singletonList(address));

        UserEntity newUserEntity = new UserEntity(user);

        assertEquals(user.getId(), newUserEntity.getId());
        assertEquals(user.getAuthId(), newUserEntity.getAuthId());
        assertEquals(user.getName(), newUserEntity.getName());
        assertEquals(user.getEmail(), newUserEntity.getEmail());
        assertEquals(user.getCpf(), newUserEntity.getCpf());
        assertEquals(user.getPhone(), newUserEntity.getPhone());
        assertEquals(user.getCreatedAt(), newUserEntity.getCreatedAt());
        assertNotNull(newUserEntity.getAddresses());
        assertEquals(1, newUserEntity.getAddresses().size());
        assertEquals(address.getId(), newUserEntity.getAddresses().get(0).getId());
    }

    @Test
    void toDomainShouldMapUserEntityToUser() {
        userEntity.setId(1L);
        userEntity.setAuthId(101L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setCpf("123.456.789-00");
        userEntity.setPhone("11987654321");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(10L);
        userEntity.setAddresses(Collections.singletonList(addressEntity));

        User domainUser = userEntity.toDomain();

        assertNotNull(domainUser);
        assertEquals(userEntity.getId(), domainUser.getId());
        assertEquals(userEntity.getAuthId(), domainUser.getAuthId());
        assertEquals(userEntity.getName(), domainUser.getName());
        assertEquals(userEntity.getEmail(), domainUser.getEmail());
        assertEquals(userEntity.getCpf(), domainUser.getCpf());
        assertEquals(userEntity.getPhone(), domainUser.getPhone());
        assertEquals(userEntity.getCreatedAt(), domainUser.getCreatedAt());
        assertNotNull(domainUser.getAddresses());
        assertEquals(1, domainUser.getAddresses().size());
        assertEquals(addressEntity.getId(), domainUser.getAddresses().get(0).getId());
    }
}
