package challenge.tech.dto.paremeter;

import challenge.tech.domain.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateProductParameter {

    @NotNull(message = "Nome é obrigatório.")
    private String name;

    @NotNull(message = "SKU é obrigatório.")
    private String sku;

    @NotNull(message = "Preço é obrigatório.")
    @PositiveOrZero(message = "Preço deve ser um valor positivo ou zero.")
    private BigDecimal price;

    public Product toDomain() {
        Product product = new Product();
        product.setName(name);
        product.setSku(sku);
        product.setPrice(price);
        return product;
    }

}
