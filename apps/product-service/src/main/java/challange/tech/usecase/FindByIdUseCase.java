package challange.tech.usecase;

import challange.tech.domain.Product;
import challange.tech.domain.User;
import challange.tech.gateway.database.ProductJpaGateway;
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

