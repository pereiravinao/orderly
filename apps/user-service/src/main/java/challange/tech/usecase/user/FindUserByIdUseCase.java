package challange.tech.usecase.user;

import challange.tech.domain.User;
import challange.tech.gateway.database.UserJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {
    private final UserJpaGateway userJpaGateway;

    public User execute(Long id) {
        return userJpaGateway.findById(id);
    }
}

