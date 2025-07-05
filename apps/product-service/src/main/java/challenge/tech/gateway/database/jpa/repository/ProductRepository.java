package challenge.tech.gateway.database.jpa.repository;

import challenge.tech.gateway.database.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    public boolean existsBySKU(String sku);

    Optional<ProductEntity> findBySKU(String sku);

}

