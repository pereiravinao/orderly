package challange.tech.usecase;

import challange.tech.domain.Product;
import challange.tech.gateway.database.ProductJpaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindAllUseCase {
    private final ProductJpaGateway productJpaGateway;

    @Autowired
    public FindAllUseCase(ProductJpaGateway productJpaGateway) {
        this.productJpaGateway = productJpaGateway;
    }

    public Page<Product> execute(Pageable pageable) {
        return productJpaGateway.findAll(pageable);
    }
}

