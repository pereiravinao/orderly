package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.exceptions.product.ProductExceptionHandler;
import challenge.tech.gateway.database.ProductJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUseCase {
    private final ProductJpaGateway productJpaGateway;

    public Product execute(Product product) {
        if (productJpaGateway.existsBySku(product.getSku())) {
            throw ProductExceptionHandler.productAlreadyExists();
        }

        return productJpaGateway.save(product);
    }
}

