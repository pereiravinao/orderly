package challenge.tech.usecase.address;

import challenge.tech.exceptions.address.AddressExceptionHandler;
import challenge.tech.gateway.database.AddressJpaGateway;
import challenge.tech.gateway.database.UserJpaGateway;
import challenge.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAddressUseCase {

    private final AddressJpaGateway addressJpaGateway;
    private final UserJpaGateway userJpaGateway;

    public void execute(Long addressId) {

        validateOwnerAddress(addressId);

        var existingAddress = addressJpaGateway.findById(addressId);

        addressJpaGateway.delete(existingAddress);

    }

    private void validateOwnerAddress(Long addressId) {
        var currentUser = SecurityUtils.getCurrentUser();
        var user = userJpaGateway.findByAuthId(currentUser.getId());
        if (user.getAddresses().stream().noneMatch(address -> address.getId().equals(addressId))) {
            throw AddressExceptionHandler.addressNotOwnedByUser();
        }
    }
}
