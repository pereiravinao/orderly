package challenge.tech.gateway.database.jpa;

import challenge.tech.domain.UserAuth;
import challenge.tech.exceptions.auth.AuthExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.UserAuthEntity;
import challenge.tech.gateway.database.jpa.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthJpaGateway {

    private final UserAuthRepository userAuthRepository;

    public UserAuth save(UserAuth userAuth) {
        log.info("Saving user auth with email: {}", userAuth.getEmail());
        var userAuthEntity = new UserAuthEntity(userAuth);
        return userAuthRepository.save(userAuthEntity).toDomain();
    }

    public UserAuth findByEmail(String email) {
        log.info("Finding user auth by email: {}", email);
        return userAuthRepository.findByEmail(email)
                .map(UserAuthEntity::toDomain)
                .orElseThrow(AuthExceptionHandler::userNotFound);
    }

    public boolean existsByEmailOrCpf(String email, String cpf) {
        return userAuthRepository.existsByEmailOrCpf(email, cpf);
    }

}
