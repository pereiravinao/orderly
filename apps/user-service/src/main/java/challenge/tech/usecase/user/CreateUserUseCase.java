package challenge.tech.usecase.user;

import challenge.tech.domain.User;
import challenge.tech.exceptions.user.UserExceptionHandler;
import challenge.tech.gateway.database.UserJpaGateway;
import challenge.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserJpaGateway userJpaGateway;

    public User execute(User user) {
        if (userJpaGateway.existsByEmailOrCpf(user.getEmail(), user.getCpf())) {
            throw UserExceptionHandler.userAlreadyExists();
        }

        if (Objects.isNull(user.getAuthId())) {
            var currentUser = SecurityUtils.getCurrentUser();
            user.setAuthId(currentUser.getId());
        }

        return userJpaGateway.save(user);
    }
}

