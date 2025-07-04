package challenge.tech.controller;

import challenge.tech.dto.parameter.LoginParameter;
import challenge.tech.dto.parameter.RegisterUserParameter;
import challenge.tech.dto.presenter.LoginResponse;
import challenge.tech.dto.presenter.RegisterUserPresenter;
import challenge.tech.usecase.LoginUseCase;
import challenge.tech.usecase.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginParameter loginRequest) {
        var userAuth = this.loginUseCase.execute(loginRequest);
        return new ResponseEntity<>(new LoginResponse(userAuth.getToken()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserPresenter> register(@Valid @RequestBody RegisterUserParameter registerRequest) {
        var userAuth = this.registerUseCase.execute(registerRequest.toDomain());
        return new ResponseEntity<>(new RegisterUserPresenter(userAuth), HttpStatus.CREATED);
    }

}