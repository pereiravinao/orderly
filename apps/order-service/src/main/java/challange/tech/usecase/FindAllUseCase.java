package challange.tech.usecase;

import challange.tech.domain.Order;
import challange.tech.gateway.database.OrderJpaGateway;
import challange.tech.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindAllUseCase {
    private final OrderJpaGateway orderJpaGateway;

    @Autowired
    public FindAllUseCase(OrderJpaGateway orderJpaGateway) {
        this.orderJpaGateway = orderJpaGateway;
    }

    public Page<Order> execute(Pageable pageable) {
        var currentUser = SecurityUtils.getCurrentUser();
        return orderJpaGateway.findAll(currentUser.getId(), pageable);
    }
}

