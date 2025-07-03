package challange.tech.usecase;

import challange.tech.domain.Product;
import challange.tech.exceptions.product.ProductExceptionHandler;
import challange.tech.gateway.database.ProductJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUseCase {
    private final ProductJpaGateway productJpaGateway;

    public Product execute(Product product) {
        if (productJpaGateway.existsBySKU(product.getSKU())) {
            throw ProductExceptionHandler.productAlreadyExists();
        }

        return productJpaGateway.save(product);
    }
}

