package challange.tech.dto.paremeter;

import challange.tech.domain.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CreateOrderParameter {

    @NotNull
    private List<Map<String, Integer>> products;

    @NotNull
    private Long paymentId;

    public Order toDomain() {
        Order order = new Order();
        order.setProducts(products);
        order.setPaymentId(paymentId);
        return order;
    }

}
