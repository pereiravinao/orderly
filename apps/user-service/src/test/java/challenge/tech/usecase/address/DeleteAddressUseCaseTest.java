package challenge.tech.usecase.address;

import challenge.tech.domain.Address;
import challenge.tech.domain.User;
import challenge.tech.dto.UserAuthDTO;
import challenge.tech.exceptions.address.AddressExceptionHandler;
import challenge.tech.gateway.database.AddressJpaGateway;
import challenge.tech.gateway.database.UserJpaGateway;
import challenge.tech.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAddressUseCaseTest {

    @Mock
    private AddressJpaGateway addressJpaGateway;

    @Mock
    private UserJpaGateway userJpaGateway;

    @InjectMocks
    private DeleteAddressUseCase deleteAddressUseCase;

    private User user;
    private UserAuthDTO userAuthDTO;
    private Address address;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        address = new Address();
        address.setId(10L);
        address.setUser(user);
        user.setAddresses(Collections.singletonList(address));

        userAuthDTO = UserAuthDTO.builder().id(1L).build();
    }

    @Test
    void shouldDeleteAddressSuccessfully() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUser).thenReturn(userAuthDTO);
            when(userJpaGateway.findByAuthId(anyLong())).thenReturn(user);
            when(addressJpaGateway.findById(anyLong())).thenReturn(address);
            doNothing().when(addressJpaGateway).delete(any(Address.class));

            deleteAddressUseCase.execute(10L);

            verify(addressJpaGateway, times(1)).findById(10L);
            verify(addressJpaGateway, times(1)).delete(address);
        }
    }

    @Test
    void shouldThrowExceptionWhenAddressNotOwnedByUser() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        Address anotherAddress = new Address();
        anotherAddress.setId(20L);
        anotherAddress.setUser(anotherUser);
        anotherUser.setAddresses(Collections.singletonList(anotherAddress));

        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUser).thenReturn(userAuthDTO);
            when(userJpaGateway.findByAuthId(anyLong())).thenReturn(user);

            assertThrows(AddressExceptionHandler.class, () -> deleteAddressUseCase.execute(20L));

            verify(addressJpaGateway, never()).findById(anyLong());
            verify(addressJpaGateway, never()).delete(any(Address.class));
        }
    }
}
