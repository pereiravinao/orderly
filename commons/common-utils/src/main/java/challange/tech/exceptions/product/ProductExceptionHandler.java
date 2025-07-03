package challange.tech.exceptions.product;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public ProductExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static ProductExceptionHandler productAlreadyExists() {
        return new ProductExceptionHandler("Produto já cadastrado.", HttpStatus.CONFLICT);
    }

    public static ProductExceptionHandler productNotFound() {
        return new ProductExceptionHandler("Produto não encontrado.", HttpStatus.NOT_FOUND);
    }

}
