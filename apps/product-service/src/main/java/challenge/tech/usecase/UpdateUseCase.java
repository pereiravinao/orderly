package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.gateway.database.ProductJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUseCase {
    private final ProductJpaGateway productJpaGateway;

    public Product execute(Long id, Product parameter) {
        var existingProduct = productJpaGateway.findById(id);

        if (existingProduct == null) {
            return null;
        }
        existingProduct.update(parameter);

        return productJpaGateway.save(existingProduct);

    }
}

