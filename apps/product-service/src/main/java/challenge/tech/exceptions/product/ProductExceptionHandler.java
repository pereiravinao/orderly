package challenge.tech.exceptions.product;

public class ProductExceptionHandler {

    public static ProductNotFoundException productNotFound() {
        return new ProductNotFoundException("Product not found");
    }

    public static ProductAlreadyExistsException productAlreadyExists() {
        return new ProductAlreadyExistsException("Product with this SKU already exists");
    }
}
