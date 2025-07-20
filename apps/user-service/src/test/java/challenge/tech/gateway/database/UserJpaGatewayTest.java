package challenge.tech.gateway.database;

import challenge.tech.domain.User;
import challenge.tech.exceptions.user.UserExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.UserEntity;
import challenge.tech.gateway.database.jpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserJpaGatewayTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserJpaGateway userJpaGateway;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setAuthId(101L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setCpf("123.456.789-00");
        user.setPhone("11987654321");

        userEntity = new UserEntity(user);
    }

    @Test
    void findAllShouldReturnPageOfUsers() {
        List<UserEntity> userEntityList = Collections.singletonList(userEntity);
        Page<UserEntity> userEntityPage = new PageImpl<>(userEntityList, PageRequest.of(0, 10), 1);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userEntityPage);

        Page<User> result = userJpaGateway.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(user.getEmail(), result.getContent().get(0).getEmail());
    }

    @Test
    void findByIdShouldReturnUserWhenFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        User result = userJpaGateway.findById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserExceptionHandler.class, () -> userJpaGateway.findById(1L));
    }

    @Test
    void saveShouldReturnSavedUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User result = userJpaGateway.save(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void deleteByIdShouldCallRepositoryDeleteById() {
        doNothing().when(userRepository).deleteById(anyLong());

        userJpaGateway.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsByEmailOrCpfShouldReturnTrueWhenExists() {
        when(userRepository.existsByEmailOrCpf(anyString(), anyString())).thenReturn(true);

        assertTrue(userJpaGateway.existsByEmailOrCpf("test@example.com", "123.456.789-00"));
    }

    @Test
    void existsByEmailOrCpfShouldReturnFalseWhenNotExists() {
        when(userRepository.existsByEmailOrCpf(anyString(), anyString())).thenReturn(false);

        assertFalse(userJpaGateway.existsByEmailOrCpf("test@example.com", "123.456.789-00"));
    }

    @Test
    void findByAuthIdShouldReturnUserWhenFound() {
        when(userRepository.findByAuthId(anyLong())).thenReturn(Optional.of(userEntity));

        User result = userJpaGateway.findByAuthId(101L);

        assertNotNull(result);
        assertEquals(user.getAuthId(), result.getAuthId());
    }

    @Test
    void findByAuthIdShouldThrowExceptionWhenNotFound() {
        when(userRepository.findByAuthId(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserExceptionHandler.class, () -> userJpaGateway.findByAuthId(101L));
    }
}
