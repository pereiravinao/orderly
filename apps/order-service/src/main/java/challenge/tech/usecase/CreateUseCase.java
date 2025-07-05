package challenge.tech.usecase;

import challenge.tech.client.PaymentServiceFeignClient;
import challenge.tech.client.ProductServiceFeignClient;
import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.client.dto.PaymentRequest;
import challenge.tech.client.dto.PaymentResponse;
import challenge.tech.client.dto.UpdateStockParameter;
import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.dto.presenter.OrderResponse;
import challenge.tech.dto.presenter.ProductOrderDetails;
import challenge.tech.enums.OrderStatus;
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
    private final ProductServiceFeignClient productServiceFeignClient;
    private final StockServiceFeignClient stockServiceFeignClient;
    private final PaymentServiceFeignClient paymentServiceFeignClient;

    public void execute(Order order, String cardNumber) {
        var savedOrder = execute(order);
        var paymentResponse = createPayment(savedOrder.getTotal(), cardNumber, savedOrder.getId());
        if (paymentResponse.getStatus().equals("FAILED")) {
            savedOrder.setStatus(OrderStatus.FECHADO_SEM_CREDITO);
        }
        savedOrder.setPaymentId(paymentResponse.getId());
        orderJpaGateway.save(savedOrder.toDomain());
    }

    public OrderResponse execute(Order order) {
        if (order.getUserId() == null) {
            var currentUser = SecurityUtils.getCurrentUser();
            order.setUserId(currentUser.getId());
        }

        order.setStatus(OrderStatus.FECHADO_COM_SUCESSO);

        order.getProducts().forEach(orderItem -> {
            var stock = stockServiceFeignClient.findByProductId(orderItem.getProductId());
            if (stock.getQuantity() < orderItem.getQuantity()) {
                order.setStatus(OrderStatus.FECHADO_SEM_ESTOQUE);
//                throw StockExceptionHandler.insufficientStock();
            } else {
                this.decrementStock(orderItem, stock.getId());
            }
        });

        order.setTotal(calculateTotal(order));
        var savedOrder = orderJpaGateway.save(order);

        return mapToOrderResponse(savedOrder);
    }

    private PaymentResponse createPayment(BigDecimal amount, String cardNumber, Long orderId) {
        var paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(amount);
        paymentRequest.setCardNumber(cardNumber);
        paymentRequest.setOrderId(orderId);
        return paymentServiceFeignClient.processPayment(paymentRequest);
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
                    var productData = productServiceFeignClient.findById(orderItem.getProductId());
                    return productData.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        List<ProductOrderDetails> productDetails = savedOrder.getProducts().stream().map(orderItem -> {
            var productData = productServiceFeignClient.findById(orderItem.getProductId());
            return new ProductOrderDetails(orderItem.getProductId(), productData.getName(), productData.getPrice(), orderItem.getQuantity());
        }).collect(Collectors.toList());

        return new OrderResponse(savedOrder, productDetails);
    }
}

