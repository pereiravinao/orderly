package challange.tech.usecase.user;

import challange.tech.gateway.database.UserJpaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {
    private final UserJpaGateway userJpaGateway;

    @Autowired
    public DeleteUserUseCase(UserJpaGateway userJpaGateway) {
        this.userJpaGateway = userJpaGateway;
    }

    public void execute(Long id) {
        userJpaGateway.deleteById(id);
    }
}

