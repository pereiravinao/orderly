package challenge.tech.usecase;

import challenge.tech.domain.UserAuth;
import challenge.tech.dto.UserAuthDTO;
import challenge.tech.dto.parameter.LoginParameter;
import challenge.tech.enums.UserRole;
import challenge.tech.exceptions.auth.AuthExceptionHandler;
import challenge.tech.gateway.database.jpa.UserAuthJpaGateway;
import challenge.tech.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserAuthJpaGateway userAuthJpaGateway;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    public UserAuth execute(LoginParameter loginRequest) {
        var userAuth = this.userAuthJpaGateway.findByEmail(loginRequest.getEmail());

        if (!this.passwordEncoder.matches(loginRequest.getPassword(), userAuth.getPassword())) {
            throw AuthExceptionHandler.invalidCredentials();
        }

        var token = this.jwtTokenService.generateToken(this.mapperToDTO(userAuth));
        userAuth.setToken(token);

        return userAuth;
    }

    private UserAuthDTO mapperToDTO(UserAuth userAuth) {
        return UserAuthDTO.builder()
                .id(userAuth.getId())
                .email(userAuth.getEmail())
                .cpf(userAuth.getCpf())
                .roles(userAuth.getRoles().stream().map(UserRole::getRole).collect(Collectors.toSet()))
                .build();
    }
}
