package challenge.tech.dto.parameter;

import challenge.tech.domain.Stock;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateStockParameter {

    @NotNull(message = "Id do produto é obrigatório.")
    private Long productId;

    @NotNull(message = "Quantidade é obrigatória.")
    @PositiveOrZero(message = "Quantidade deve ser um valor positivo ou zero.")
    private Integer quantity;

    public Stock toDomain() {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setQuantity(quantity);
        return stock;
    }

}
