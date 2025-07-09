package challenge.tech.usecase;

import challenge.tech.client.PaymentServiceFeignClient;
import challenge.tech.client.ProductServiceFeignClient;
import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.client.dto.PaymentRequest;
import challenge.tech.client.dto.PaymentResponse;
import challenge.tech.client.dto.UpdateStockParameter;
import challenge.tech.domain.Order;
import challenge.tech.domain.orderItem.OrderItem;
import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessOrderUseCase {

    private final CreateUseCase createUseCase;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final StockServiceFeignClient stockServiceFeignClient;
    private final PaymentServiceFeignClient paymentServiceFeignClient;

    public void execute(OrderReceiverDTO orderReceiverDTO) {
        Order order = new Order();
        order.setUserId(orderReceiverDTO.getUser().getId());

        List<OrderItem> orderItems = validateAndBuildOrderItems(orderReceiverDTO);
        order.setProducts(orderItems);

        if (!checkStock(order)) {
            order.setStatus(OrderStatus.FECHADO_SEM_ESTOQUE);
            order.setTotal(BigDecimal.ZERO);
            createUseCase.execute(order);
            return;
        }

        decrementStock(order);

        order.setTotal(calculateTotal(order));
        order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        Order savedOrder = createUseCase.execute(order);

        PaymentResponse paymentResponse = createPayment(savedOrder, orderReceiverDTO.getPayment().getCardNumber());
        savedOrder.setPaymentId(paymentResponse.getId());

        createUseCase.execute(savedOrder);
    }

    private List<OrderItem> validateAndBuildOrderItems(OrderReceiverDTO orderReceiverDTO) {
        List<OrderItem> items = new ArrayList<>();
        orderReceiverDTO.getItems().forEach(item -> {
            var product = productServiceFeignClient.findBySku(item.getSku());
            items.add(new OrderItem(product.getId(), item.getQuantity()));
        });
        return items;
    }

    private boolean checkStock(Order order) {
        return order.getProducts().stream().allMatch(item -> {
            var stock = stockServiceFeignClient.findByProductId(item.getProductId());
            return stock.getQuantity() >= item.getQuantity();
        });
    }

    private void decrementStock(Order order) {
        order.getProducts().forEach(item -> {
            var stock = stockServiceFeignClient.findByProductId(item.getProductId());
            UpdateStockParameter updateStockParameter = new UpdateStockParameter();
            updateStockParameter.setProductId(item.getProductId());
            updateStockParameter.setQuantity(-item.getQuantity());
            stockServiceFeignClient.update(stock.getId(), updateStockParameter);
        });
    }

    private void incrementStock(Order order) {
        order.getProducts().forEach(item -> {
            var stock = stockServiceFeignClient.findByProductId(item.getProductId());
            UpdateStockParameter updateStockParameter = new UpdateStockParameter();
            updateStockParameter.setProductId(item.getProductId());
            updateStockParameter.setQuantity(item.getQuantity()); // Positive quantity to revert
            stockServiceFeignClient.update(stock.getId(), updateStockParameter);
        });
    }

    private BigDecimal calculateTotal(Order order) {
        return order.getProducts().stream()
                .map(item -> {
                    var productData = productServiceFeignClient.findById(item.getProductId());
                    return productData.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PaymentResponse createPayment(Order order, String cardNumber) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(order.getTotal());
        paymentRequest.setCardNumber(cardNumber);
        paymentRequest.setOrderId(order.getId());
        return paymentServiceFeignClient.processPayment(paymentRequest);
    }
}
