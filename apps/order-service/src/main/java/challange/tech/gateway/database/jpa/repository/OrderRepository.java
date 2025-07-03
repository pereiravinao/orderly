package challange.tech.gateway.database.jpa.repository;

import challange.tech.enums.OrderStatus;
import challange.tech.gateway.database.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUserId(Long productId);

    List<OrderEntity> findByStatus(OrderStatus orderStatus);

    Page<OrderEntity> findAllByUserId(Long userId, Pageable pageable);
}

