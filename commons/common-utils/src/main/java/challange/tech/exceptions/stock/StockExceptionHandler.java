package challange.tech.exceptions.stock;


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

    public static StockExceptionHandler insufficientStock() {
        return new StockExceptionHandler("Estoque insuficiente.", HttpStatus.BAD_REQUEST);
    }

}
