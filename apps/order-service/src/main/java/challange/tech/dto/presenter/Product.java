package challange.tech.dto.presenter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String SKU;
    private BigDecimal price;
    private LocalDateTime createdAt;
}
