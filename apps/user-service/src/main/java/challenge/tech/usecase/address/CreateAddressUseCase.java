package challenge.tech.usecase.address;

import challenge.tech.domain.Address;
import challenge.tech.domain.User;
import challenge.tech.gateway.database.AddressJpaGateway;
import challenge.tech.gateway.database.UserJpaGateway;
import challenge.tech.utils.SecurityUtils;
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
