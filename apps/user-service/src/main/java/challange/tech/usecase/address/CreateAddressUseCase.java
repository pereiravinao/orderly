package challange.tech.usecase.address;

import challange.tech.domain.Address;
import challange.tech.domain.User;
import challange.tech.gateway.database.AddressJpaGateway;
import challange.tech.gateway.database.UserJpaGateway;
import challange.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAddressUseCase {

    private final AddressJpaGateway addressJpaGateway;
    private final UserJpaGateway userJpaGateway;

    public Address execute(Address addressParameter) {
        addressParameter.setUser(getUser());
        return addressJpaGateway.save(addressParameter);
    }

    private User getUser() {
        var currentUser = SecurityUtils.getCurrentUser();
        return userJpaGateway.findByAuthId(currentUser.getId());
    }
}
