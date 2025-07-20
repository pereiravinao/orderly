package challenge.tech.gateway.database;

import challenge.tech.domain.Address;
import challenge.tech.domain.User;
import challenge.tech.exceptions.user.UserExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.AddressEntity;
import challenge.tech.gateway.database.jpa.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressJpaGatewayTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressJpaGateway addressJpaGateway;

    private Address address;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setId(1L);
        address.setStreet("Test Street");
        address.setNumber("123");
        address.setCity("Test City");
        address.setState("TS");
        address.setZipCode("12345-678");
        address.setUser(new User());

        addressEntity = new AddressEntity(address);
    }

    @Test
    void saveShouldReturnSavedAddress() {
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        Address result = addressJpaGateway.save(address);

        assertNotNull(result);
        assertEquals(address.getId(), result.getId());
        verify(addressRepository, times(1)).save(any(AddressEntity.class));
    }

    @Test
    void findByIdShouldReturnAddressWhenFound() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));

        Address result = addressJpaGateway.findById(1L);

        assertNotNull(result);
        assertEquals(address.getId(), result.getId());
        verify(addressRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserExceptionHandler.class, () -> addressJpaGateway.findById(1L));
        verify(addressRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteShouldCallRepositoryDelete() {
        doNothing().when(addressRepository).delete(any(AddressEntity.class));

        addressJpaGateway.delete(address);

        verify(addressRepository, times(1)).delete(any(AddressEntity.class));
    }
}
