package challenge.tech.exceptions.user;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public UserExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static UserExceptionHandler userAlreadyExists() {
        return new UserExceptionHandler("Usuário já cadastrado.", HttpStatus.CONFLICT);
    }

    public static UserExceptionHandler userNotFound() {
        return new UserExceptionHandler("Usuário não encontrado.", HttpStatus.NOT_FOUND);
    }

}
