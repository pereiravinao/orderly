package challange.tech.controller;

import challange.tech.dto.parameter.LoginParameter;
import challange.tech.dto.parameter.RegisterUserParameter;
import challange.tech.dto.presenter.LoginResponse;
import challange.tech.dto.presenter.RegisterUserPresenter;
import challange.tech.usecase.LoginUseCase;
import challange.tech.usecase.RegisterUseCase;
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