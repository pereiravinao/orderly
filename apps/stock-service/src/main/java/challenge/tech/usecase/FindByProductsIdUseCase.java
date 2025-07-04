package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.exceptions.stock.StockExceptionHandler;
import challenge.tech.gateway.database.StockJpaGateway;
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

