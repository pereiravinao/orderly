package challenge.tech.controller;

import challenge.tech.domain.User;
import challenge.tech.dto.paremeter.CreateUserParameter;
import challenge.tech.usecase.user.CreateUserUseCase;
import challenge.tech.usecase.user.DeleteUserUseCase;
import challenge.tech.usecase.user.FindAllUsersUseCase;
import challenge.tech.usecase.user.FindUserByIdUseCase;
import challenge.tech.usecase.user.UpdateUserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private FindAllUsersUseCase findAllUsersUseCase;
    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;
    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private UpdateUserUseCase updateUserUseCase;
    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllUsers_shouldReturnPageOfUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        List<User> userList = Collections.singletonList(user);
        Page<User> userPage = new PageImpl<>(userList, PageRequest.of(0, 10), 1);

        when(findAllUsersUseCase.execute(any(Pageable.class))).thenReturn(userPage);

        mockMvc.perform(get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test User"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");

        when(findUserByIdUseCase.execute(anyLong())).thenReturn(user);

        mockMvc.perform(get("/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        CreateUserParameter parameter = new CreateUserParameter(1L, "New User", "123456789", "new@example.com", "12345678900");
        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setName("New User");

        when(createUserUseCase.execute(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New User"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        User parameter = new User();
        parameter.setName("Updated User");
        parameter.setPhone("987654321");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Updated User");

        when(updateUserUseCase.execute(anyLong(), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        doNothing().when(deleteUserUseCase).execute(anyLong());

        mockMvc.perform(delete("/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
