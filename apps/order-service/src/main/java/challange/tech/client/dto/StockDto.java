package challange.tech.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}