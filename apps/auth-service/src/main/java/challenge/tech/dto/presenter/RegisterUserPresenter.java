package challenge.tech.dto.presenter;

import challenge.tech.domain.UserAuth;
import lombok.Getter;

@Getter
public class RegisterUserPresenter {
    private final Long id;
    private final String email;
    private final String token;

    public RegisterUserPresenter(UserAuth userAuth) {
        this.id = userAuth.getId();
        this.email = userAuth.getEmail();
        this.token = userAuth.getToken();
    }
}
