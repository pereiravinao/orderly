package challange.tech.gateway.database.jpa.repository;

import challange.tech.gateway.database.jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}

