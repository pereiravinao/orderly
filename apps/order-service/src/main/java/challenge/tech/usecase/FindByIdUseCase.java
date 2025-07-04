package challenge.tech.usecase;

import challenge.tech.domain.Order;
import challenge.tech.exceptions.order.OrderExceptionHandler;
import challenge.tech.gateway.database.OrderJpaGateway;
import challenge.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdUseCase {
    private final OrderJpaGateway orderJpaGateway;

    public Order execute(Long id) {
        var currentUser = SecurityUtils.getCurrentUser();
        if (!currentUser.getId().equals(orderJpaGateway.findById(id).getUserId())) {
            throw OrderExceptionHandler.notOwnedByUser();
        }
        return orderJpaGateway.findById(id);
    }
}

