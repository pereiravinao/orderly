package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.StockJpaGateway;
import challenge.tech.service.ProductValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUseCase {
    private final StockJpaGateway stockJpaGateway;
    private final ProductValidationService productValidationService;

    public Stock execute(Long id, Long productId, Integer quantity) {
        var existingStock = stockJpaGateway.findById(id);

        productValidationService.validateProductExistence(productId);

        if (existingStock.getQuantity() + quantity < 0) {
            throw StockExceptionHandler.insufficientStock();
        }

        existingStock.setQuantity(existingStock.getQuantity() + quantity);

        return stockJpaGateway.save(existingStock);
    }
}


