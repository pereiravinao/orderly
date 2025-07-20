package challenge.tech.dto.paremeter;

import challenge.tech.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductParameter {
    @NotBlank
    private String name;
    @NotBlank
    private String sku;
    @NotNull
    private BigDecimal price;

    public Product toDomain() {
        Product product = new Product();
        product.setName(this.name);
        product.setSku(this.sku);
        product.setPrice(this.price);
        return product;
    }
}
