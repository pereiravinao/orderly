package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.gateway.database.StockJpaGateway;
import challenge.tech.service.ProductValidationService;
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

