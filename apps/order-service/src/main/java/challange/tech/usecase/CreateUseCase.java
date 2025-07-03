package challange.tech.usecase;

import challange.tech.domain.Order;
import challange.tech.gateway.database.OrderJpaGateway;
import challange.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateUseCase {
    private final OrderJpaGateway orderJpaGateway;

    public Order execute(Order order) {
        var currentUser = SecurityUtils.getCurrentUser();
        order.setUserId(currentUser.getId());
        order.setTotal(calculateTotal(order));
        return orderJpaGateway.save(order);
    }

    private BigDecimal calculateTotal(Order order) {
        return order.getProducts().stream().flatMap(
                product -> product.values().stream()
                        .map(integer -> integer * 10L)
                        .map(BigDecimal::valueOf) // Assuming each product has a fixed price of 10
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

