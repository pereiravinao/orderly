package challange.tech.gateway.database.jpa.repository;

import challange.tech.gateway.database.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    public boolean existsBySKU(String sku);

}

