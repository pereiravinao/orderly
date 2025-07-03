package challange.tech.exceptions.address;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AddressExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public AddressExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static AddressExceptionHandler addressNotOwnedByUser() {
        return new AddressExceptionHandler("Endereço não pertence ao usuário.", HttpStatus.FORBIDDEN);
    }

}
