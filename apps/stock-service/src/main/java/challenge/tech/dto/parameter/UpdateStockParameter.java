package challenge.tech.dto.parameter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStockParameter {
    private Long productId;
    private Integer quantity;
}