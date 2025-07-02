package challange.tech.usecase;

import challange.tech.domain.UserAuth;
import challange.tech.dto.UserAuthDTO;
import challange.tech.enums.UserRole;
import challange.tech.exceptions.auth.AuthExceptionHandler;
import challange.tech.gateway.database.jpa.UserAuthJpaGateway;
import challange.tech.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisterUseCase {
    private final UserAuthJpaGateway userAuthJpaGateway;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public UserAuth execute(UserAuth userAuth) {
        if (this.userAuthJpaGateway.existsByEmailOrCpf(userAuth.getEmail(), userAuth.getCpf())) {
            throw AuthExceptionHandler.userAlreadyExists();
        }

        var passEncode = this.passwordEncoder.encode(userAuth.getPassword());
        userAuth.setPassword(passEncode);

        userAuth = this.userAuthJpaGateway.save(userAuth);

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
}
