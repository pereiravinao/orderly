package challange.tech.usecase;

import challange.tech.domain.UserAuth;
import challange.tech.dto.UserAuthDTO;
import challange.tech.dto.parameter.LoginParameter;
import challange.tech.exceptions.auth.AuthExceptionHandler;
import challange.tech.gateway.database.jpa.UserAuthJpaGateway;
import challange.tech.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .createdAt(userAuth.getCreatedAt())
                .token(userAuth.getToken())
                .build();
    }
    
}
