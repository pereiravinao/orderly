package challenge.tech.exceptions.order;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public OrderExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static OrderExceptionHandler notOwnedByUser() {
        return new OrderExceptionHandler("Você não possui permissão para acessar esse pedido.", HttpStatus.FORBIDDEN);
    }

}
