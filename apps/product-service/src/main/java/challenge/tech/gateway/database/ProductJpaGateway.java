package challenge.tech.gateway.database;

import challenge.tech.domain.Product;
import challenge.tech.exceptions.product.ProductExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.ProductEntity;
import challenge.tech.gateway.database.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductJpaGateway {

    private final ProductRepository productRepository;

    @Autowired
    public ProductJpaGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductEntity::toDomain);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .map(ProductEntity::toDomain)
                .orElseThrow(ProductExceptionHandler::productNotFound);
    }

    public Product save(Product product) {
        return productRepository.save(new ProductEntity(product)).toDomain();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public boolean existsBySKU(String SKU) {
        return productRepository.existsBySKU(SKU);
    }

}

