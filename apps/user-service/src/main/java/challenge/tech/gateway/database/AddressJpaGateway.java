package challenge.tech.gateway.database;

import challenge.tech.domain.Address;
import challenge.tech.exceptions.user.UserExceptionHandler;
import challenge.tech.gateway.database.jpa.entity.AddressEntity;
import challenge.tech.gateway.database.jpa.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressJpaGateway {

    private final AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(new AddressEntity(address)).toDomain();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .map(AddressEntity::toDomain)
                .orElseThrow(UserExceptionHandler::userNotFound);
    }

    public void delete(Address address) {
        addressRepository.delete(new AddressEntity(address));
    }

}
