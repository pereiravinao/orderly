package challange.tech.usecase;

import challange.tech.domain.Stock;
import challange.tech.exceptions.stock.StockExceptionHandler;
import challange.tech.gateway.database.StockJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByProductsIdUseCase {
    private final StockJpaGateway stockJpaGateway;

    public Stock execute(Long productId) {
        return stockJpaGateway.findByProductId(productId)
                .orElseThrow(StockExceptionHandler::notFound);
    }
}

