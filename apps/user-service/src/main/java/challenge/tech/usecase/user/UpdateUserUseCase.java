package challenge.tech.usecase.user;

import challenge.tech.domain.User;
import challenge.tech.gateway.database.UserJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserJpaGateway userJpaGateway;

    public User execute(Long id, User userParameter) {
        var existingUser = userJpaGateway.findById(id);

        existingUser.update(userParameter);

        return userJpaGateway.save(existingUser);

    }
}

