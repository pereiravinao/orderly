package challange.tech.usecase;

import challange.tech.domain.Product;
import challange.tech.gateway.database.ProductJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUseCase {
    private final ProductJpaGateway productJpaGateway;

    public Product execute(Long id, Product parameter) {
        var existingProduct = productJpaGateway.findById(id);

        existingProduct.update(parameter);

        return productJpaGateway.save(existingProduct);

    }
}

