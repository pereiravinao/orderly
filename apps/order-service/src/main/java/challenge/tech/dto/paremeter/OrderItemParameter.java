package challenge.tech.dto.paremeter;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemParameter {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
