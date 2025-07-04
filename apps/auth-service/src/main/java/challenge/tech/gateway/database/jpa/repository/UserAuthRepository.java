package challenge.tech.gateway.database.jpa.repository;

import challenge.tech.gateway.database.jpa.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {

    Optional<UserAuthEntity> findByEmail(String email);

    boolean existsByEmailOrCpf(String email, String cpf);

}
