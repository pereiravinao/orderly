package challange.tech.usecase;

import challange.tech.client.ProductClient;
import challange.tech.domain.Order;
import challange.tech.dto.presenter.OrderResponse;
import challange.tech.dto.presenter.ProductOrderDetails;
import challange.tech.gateway.database.OrderJpaGateway;
import challange.tech.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateUseCase {
    private final OrderJpaGateway orderJpaGateway;
    private final ProductClient productClient;

    public OrderResponse execute(Order order) {
        var currentUser = SecurityUtils.getCurrentUser();
        order.setUserId(currentUser.getId());
        order.setTotal(calculateTotal(order));
        var savedOrder = orderJpaGateway.save(order);

        return mapToOrderResponse(savedOrder);
    }

    private BigDecimal calculateTotal(Order order) {
        return order.getProducts().stream()
                .map(orderItem -> {
                    var productData = productClient.findById(orderItem.getProductId());
                    return productData.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        List<ProductOrderDetails> productDetails = savedOrder.getProducts().stream().map(orderItem -> {
            var productData = productClient.findById(orderItem.getProductId());
            return new ProductOrderDetails(orderItem.getProductId(), productData.getName(), productData.getPrice(), orderItem.getQuantity());
        }).collect(Collectors.toList());

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getPaymentId(),
                savedOrder.getStatus(),
                savedOrder.getTotal(),
                savedOrder.getCreatedAt(),
                productDetails
        );
    }
}

