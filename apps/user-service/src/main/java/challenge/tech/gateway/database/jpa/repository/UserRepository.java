package challenge.tech.gateway.database.jpa.repository;

import challenge.tech.gateway.database.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmailOrCpf(String email, String cpf);

    Optional<UserEntity> findByAuthId(Long authId);
}

