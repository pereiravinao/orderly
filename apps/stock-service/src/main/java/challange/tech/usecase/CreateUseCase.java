package challange.tech.usecase;

import challange.tech.domain.Stock;
import challange.tech.gateway.database.StockJpaGateway;
import challange.tech.service.ProductValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUseCase {
    private final StockJpaGateway stockJpaGateway;
    private final ProductValidationService productValidationService;

    public Stock execute(Stock stockParameter) {
        productValidationService.validateProductExistence(stockParameter.getProductId());
        var stock = stockJpaGateway.findByProductId(stockParameter.getProductId())
                .map(existingStock -> existingStock.plusQuantity(stockParameter.getQuantity()))
                .orElse(stockParameter);

        return stockJpaGateway.save(stock);
    }
}

