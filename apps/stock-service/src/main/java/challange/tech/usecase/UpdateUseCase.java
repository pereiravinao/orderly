package challange.tech.usecase;

import challange.tech.domain.Stock;
import challange.tech.gateway.database.StockJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUseCase {
    private final StockJpaGateway stockJpaGateway;

    public Stock execute(Long id, Stock parameter) {
        var existingStock = stockJpaGateway.findById(id);

        existingStock.update(parameter);

        return stockJpaGateway.save(existingStock);

    }
}

