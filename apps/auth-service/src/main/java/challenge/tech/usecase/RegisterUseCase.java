package challenge.tech.usecase;

import challenge.tech.client.UserClient;
import challenge.tech.domain.UserAuth;
import challenge.tech.dto.UserAuthDTO;
import challenge.tech.dto.parameter.CreateUserParameter;
import challenge.tech.enums.UserRole;
import challenge.tech.exceptions.auth.AuthExceptionHandler;
import challenge.tech.gateway.database.jpa.UserAuthJpaGateway;
import challenge.tech.services.JwtTokenService;
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