package challenge.tech.usecase;

import challenge.tech.domain.Order;
import challenge.tech.gateway.database.OrderJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUseCase {

    private final OrderJpaGateway orderJpaGateway;

    public Order execute(Order order) {
        return orderJpaGateway.save(order);
    }

}

