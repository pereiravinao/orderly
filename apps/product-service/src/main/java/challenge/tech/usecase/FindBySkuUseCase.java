package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.gateway.database.ProductJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBySkuUseCase {
    private final ProductJpaGateway productJpaGateway;

    public Product execute(String sku) {
        return productJpaGateway.findBySku(sku);
    }
}

