package challenge.tech.usecase;

import challenge.tech.gateway.database.ProductJpaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUseCase {
    private final ProductJpaGateway productJpaGateway;

    @Autowired
    public DeleteUseCase(ProductJpaGateway productJpaGateway) {
        this.productJpaGateway = productJpaGateway;
    }

    public void execute(Long id) {
        productJpaGateway.deleteById(id);
    }
}

