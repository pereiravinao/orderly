package challange.tech.exceptions.stock.product;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StockExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public StockExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static StockExceptionHandler notFound() {
        return new StockExceptionHandler("Estoque não encontrado.", HttpStatus.NOT_FOUND);
    }

}
