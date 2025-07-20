package challenge.tech.usecase.user;

import challenge.tech.domain.User;
import challenge.tech.gateway.database.UserJpaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    private UserJpaGateway userJpaGateway;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private User existingUser;
    private User userParameter;

    @BeforeEach
    void setUp() {
        existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Original Name");
        existingUser.setPhone("111111111");

        userParameter = new User();
        userParameter.setName("Updated Name");
        userParameter.setPhone("222222222");
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        when(userJpaGateway.findById(anyLong())).thenReturn(existingUser);
        when(userJpaGateway.save(any(User.class))).thenReturn(existingUser);

        User result = updateUserUseCase.execute(1L, userParameter);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("222222222", result.getPhone());
    }
}
