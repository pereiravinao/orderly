package challenge.tech.usecase;

import challenge.tech.client.PaymentServiceFeignClient;
import challenge.tech.client.ProductClient;
import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.client.dto.PaymentRequest;
import challenge.tech.client.dto.UpdateStockParameter;
import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.dto.presenter.OrderResponse;
import challenge.tech.dto.presenter.ProductOrderDetails;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.OrderJpaGateway;
import challenge.tech.utils.SecurityUtils;
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
    private final PaymentServiceFeignClient paymentServiceFeignClient;

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

