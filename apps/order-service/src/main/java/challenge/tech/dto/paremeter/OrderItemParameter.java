package challenge.tech.dto.paremeter;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemParameter {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
