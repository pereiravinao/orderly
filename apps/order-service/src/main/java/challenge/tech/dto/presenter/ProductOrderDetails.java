package challenge.tech.dto.presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductOrderDetails {
    private Long productId;
    private String name;
    private BigDecimal unitPrice;
    private Integer quantity;
}
