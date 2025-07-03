package challange.tech.usecase.user;

import challange.tech.domain.User;
import challange.tech.gateway.database.UserJpaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindAllUsersUseCase {
    private final UserJpaGateway userJpaGateway;

    @Autowired
    public FindAllUsersUseCase(UserJpaGateway userJpaGateway) {
        this.userJpaGateway = userJpaGateway;
    }

    public Page<User> execute(Pageable pageable) {
        return userJpaGateway.findAll(pageable);
    }
}

