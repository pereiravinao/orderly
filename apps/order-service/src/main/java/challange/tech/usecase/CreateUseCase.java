package challange.tech.usecase;

import challange.tech.client.ProductClient;
import challange.tech.client.StockServiceFeignClient;
import challange.tech.client.dto.UpdateStockParameter;
import challange.tech.domain.Order;
import challange.tech.domain.orderItem.OrderItem;
import challange.tech.dto.presenter.OrderResponse;
import challange.tech.dto.presenter.ProductOrderDetails;
import challange.tech.exceptions.stock.StockExceptionHandler;
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
    private final StockServiceFeignClient stockServiceFeignClient;

    public OrderResponse execute(Order order) {
        var currentUser = SecurityUtils.getCurrentUser();
        order.setUserId(currentUser.getId());

        order.getProducts().forEach(orderItem -> {
            var stock = stockServiceFeignClient.findByProductId(orderItem.getProductId());
            if (stock.getQuantity() < orderItem.getQuantity()) {
                throw StockExceptionHandler.insufficientStock();
            }
            this.decrementStock(orderItem, stock.getId());

        });

        order.setTotal(calculateTotal(order));
        var savedOrder = orderJpaGateway.save(order);
        return mapToOrderResponse(savedOrder);
    }

    private void decrementStock(OrderItem orderItem, Long stockId) {
        UpdateStockParameter updateStockParameter = new UpdateStockParameter();
        updateStockParameter.setProductId(orderItem.getProductId());
        updateStockParameter.setQuantity(-orderItem.getQuantity());
        stockServiceFeignClient.update(stockId, updateStockParameter);
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

