package challange.tech.usecase;

import challange.tech.client.UserClient;
import challange.tech.domain.UserAuth;
import challange.tech.dto.UserAuthDTO;
import challange.tech.dto.parameter.CreateUserParameter;
import challange.tech.enums.UserRole;
import challange.tech.exceptions.auth.AuthExceptionHandler;
import challange.tech.gateway.database.jpa.UserAuthJpaGateway;
import challange.tech.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisterUseCase {
    private final UserAuthJpaGateway userAuthJpaGateway;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final UserClient userClient;

    @Transactional
    public UserAuth execute(UserAuth userAuth) {
        if (this.userAuthJpaGateway.existsByEmailOrCpf(userAuth.getEmail(), userAuth.getCpf())) {
            throw AuthExceptionHandler.userAlreadyExists();
        }

        var passEncode = this.passwordEncoder.encode(userAuth.getPassword());
        userAuth.setPassword(passEncode);

        var userAuthId = this.userAuthJpaGateway.save(userAuth).getId();
        userAuth.setId(userAuthId);
        this.createUserInUserService(userAuth);

        userAuth.setToken(this.jwtTokenService.generateToken(this.mapperToDTO(userAuth)));

        return userAuth;
    }

    private UserAuthDTO mapperToDTO(UserAuth userAuth) {
        return UserAuthDTO.builder()
                .id(userAuth.getId())
                .email(userAuth.getEmail())
                .cpf(userAuth.getCpf())
                .roles(userAuth.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
                .build();
    }

    private void createUserInUserService(UserAuth userAuth) {
        userClient.createUser(new CreateUserParameter(
                userAuth.getId(),
                userAuth.getName(),
                userAuth.getPhone(),
                userAuth.getEmail(),
                userAuth.getCpf()
        ));
    }
}