package challenge.tech.usecase.user;

import challenge.tech.domain.User;
import challenge.tech.gateway.database.UserJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUsersUseCaseTest {

    @Mock
    private UserJpaGateway userJpaGateway;

    @InjectMocks
    private FindAllUsersUseCase findAllUsersUseCase;

    @Test
    void shouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(userJpaGateway.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<User> result = findAllUsersUseCase.execute(pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
    }
}
