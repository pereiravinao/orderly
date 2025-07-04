package challange.tech.usecase.user;

import challange.tech.domain.User;
import challange.tech.exceptions.user.UserExceptionHandler;
import challange.tech.gateway.database.UserJpaGateway;
import challange.tech.utils.SecurityUtils;
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

