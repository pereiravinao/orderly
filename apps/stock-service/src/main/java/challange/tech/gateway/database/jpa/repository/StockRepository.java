package challange.tech.gateway.database.jpa.repository;

import challange.tech.gateway.database.jpa.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByProductId(Long productId);
}

