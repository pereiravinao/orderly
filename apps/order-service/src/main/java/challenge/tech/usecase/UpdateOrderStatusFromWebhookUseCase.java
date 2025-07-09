package challenge.tech.usecase;

import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.client.dto.UpdateStockParameter;
import challenge.tech.domain.Order;
import challenge.tech.enums.OrderStatus;
import challenge.tech.enums.PaymentStatus;
import challenge.tech.gateway.database.OrderJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusFromWebhookUseCase {

    private final OrderJpaGateway orderJpaGateway;
    private final StockServiceFeignClient stockServiceFeignClient;

    public void execute(Long orderId, PaymentStatus paymentStatus) {
        Order order = orderJpaGateway.findById(orderId);

        if (order == null) {
            return;
        }

        var orderStatus = mapPaymentStatusToOrderStatus(paymentStatus);

        if (orderStatus.equals(OrderStatus.FECHADO_COM_SUCESSO)) {
            order.setStatus(OrderStatus.FECHADO_COM_SUCESSO);
        } else if (orderStatus.equals(OrderStatus.FECHADO_SEM_CREDITO)) {
            order.setStatus(OrderStatus.FECHADO_SEM_CREDITO);
            incrementStock(order);
        }
        orderJpaGateway.save(order);
    }

    private void incrementStock(Order order) {
        order.getProducts().forEach(item -> {
            UpdateStockParameter updateStockParameter = new UpdateStockParameter();
            updateStockParameter.setProductId(item.getProductId());
            updateStockParameter.setQuantity(item.getQuantity());
            stockServiceFeignClient.update(stockServiceFeignClient.findByProductId(item.getProductId()).getId(), updateStockParameter);
        });
    }

    private OrderStatus mapPaymentStatusToOrderStatus(PaymentStatus paymentStatus) {
        if (paymentStatus.equals(PaymentStatus.SUCCESS)) {
            return OrderStatus.FECHADO_COM_SUCESSO;
        } else if (paymentStatus.equals(PaymentStatus.FAILED)) {
            return OrderStatus.FECHADO_SEM_CREDITO;
        } else {
            return OrderStatus.AGUARDANDO_PAGAMENTO;
        }
    }
}