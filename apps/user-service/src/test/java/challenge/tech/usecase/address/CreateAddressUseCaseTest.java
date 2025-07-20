package challenge.tech.usecase.address;

import challenge.tech.domain.Address;
import challenge.tech.domain.User;
import challenge.tech.dto.UserAuthDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAddressUseCaseTest {

    @Mock
    private AddressJpaGateway addressJpaGateway;

    @Mock
    private UserJpaGateway userJpaGateway;

    @InjectMocks
    private CreateAddressUseCase createAddressUseCase;

    private Address addressParameter;
    private User user;
    private UserAuthDTO userAuthDTO;

    @BeforeEach
    void setUp() {
        addressParameter = new Address();
        addressParameter.setStreet("Test Street");

        user = new User();
        user.setId(1L);

        userAuthDTO = UserAuthDTO.builder().id(1L).build();
    }

    @Test
    void shouldCreateAddressSuccessfully() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUser).thenReturn(userAuthDTO);
            when(userJpaGateway.findByAuthId(anyLong())).thenReturn(user);
            when(addressJpaGateway.save(any(Address.class))).thenReturn(addressParameter);

            Address result = createAddressUseCase.execute(addressParameter);

            assertNotNull(result);
            assertEquals("Test Street", result.getStreet());
            assertEquals(user, result.getUser());
        }
    }
}
