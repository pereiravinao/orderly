package challange.tech.usecase;

import challange.tech.domain.Stock;
import challange.tech.exceptions.stock.StockExceptionHandler;
import challange.tech.gateway.database.StockJpaGateway;
import challange.tech.service.ProductValidationService;
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


