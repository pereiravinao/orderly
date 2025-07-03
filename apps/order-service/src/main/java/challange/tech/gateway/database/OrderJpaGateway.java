package challange.tech.gateway.database;

import challange.tech.domain.Order;
import challange.tech.exceptions.stock.StockExceptionHandler;
import challange.tech.gateway.database.jpa.entity.OrderEntity;
import challange.tech.gateway.database.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderJpaGateway {

    private final OrderRepository orderRepository;

    public Page<Order> findAll(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).map(OrderEntity::toDomain);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .map(OrderEntity::toDomain)
                .orElseThrow(StockExceptionHandler::notFound);
    }

    public Order save(Order stock) {
        return orderRepository.save(new OrderEntity(stock)).toDomain();
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

}

