package challenge.tech.gateway.database;

import challenge.tech.domain.User;
import challenge.tech.exceptions.user.UserExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.UserEntity;
import challenge.tech.gateway.database.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserJpaGateway {

    private final UserRepository userRepository;

    @Autowired
    public UserJpaGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserEntity::toDomain);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .map(UserEntity::toDomain)
                .orElseThrow(UserExceptionHandler::userNotFound);
    }

    public User save(User user) {
        return userRepository.save(new UserEntity(user)).toDomain();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmailOrCpf(String email, String cpf) {
        return userRepository.existsByEmailOrCpf(email, cpf);
    }

    public User findByAuthId(Long authId) {
        return userRepository.findByAuthId(authId)
                .map(UserEntity::toDomain)
                .orElseThrow(UserExceptionHandler::userNotFound);
    }
}

