package challange.tech.usecase.user;

import challange.tech.domain.User;
import challange.tech.exceptions.user.UserExceptionHandler;
import challange.tech.gateway.database.UserJpaGateway;
import challange.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserJpaGateway userJpaGateway;

    public User execute(User user) {
        var currentUser = SecurityUtils.getCurrentUser();

        if (userJpaGateway.existsByEmailOrCpf(currentUser.getEmail(), currentUser.getCpf())) {
            throw UserExceptionHandler.userAlreadyExists();
        }

        user.setCpf(currentUser.getCpf());
        user.setEmail(currentUser.getEmail());
        user.setAuthId(currentUser.getId());

        return userJpaGateway.save(user);
    }
}

