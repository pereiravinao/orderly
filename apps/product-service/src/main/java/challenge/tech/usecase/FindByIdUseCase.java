package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.gateway.database.ProductJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdUseCase {
    private final ProductJpaGateway productJpaGateway;

    public Product execute(Long id) {
        return productJpaGateway.findById(id);
    }
}

