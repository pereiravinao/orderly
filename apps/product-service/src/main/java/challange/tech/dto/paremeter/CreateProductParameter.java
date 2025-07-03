package challange.tech.dto.paremeter;

import challange.tech.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
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
        product.setSKU(sku);
        product.setPrice(price);
        return product;
    }

}
