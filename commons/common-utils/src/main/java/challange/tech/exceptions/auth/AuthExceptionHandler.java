package challange.tech.exceptions.auth;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public AuthExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static AuthExceptionHandler invalidCredentials() {
        return new AuthExceptionHandler("Email ou senha inválidos.", HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptionHandler userAlreadyExists() {
        return new AuthExceptionHandler("Usuário já cadastrado.", HttpStatus.CONFLICT);
    }

    public static AuthExceptionHandler unauthorized() {
        return new AuthExceptionHandler("Acesso não autorizado.", HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptionHandler forbidden() {
        return new AuthExceptionHandler("Sem permissão para acessar este recurso.", HttpStatus.FORBIDDEN);
    }

    public static AuthExceptionHandler userNotFound() {
        return new AuthExceptionHandler("Usuário não encontrado.", HttpStatus.NOT_FOUND);
    }

}
