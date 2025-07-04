package challenge.tech.usecase;

import challenge.tech.domain.Stock;
import challenge.tech.gateway.database.StockJpaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindAllUseCase {
    private final StockJpaGateway stockJpaGateway;

    @Autowired
    public FindAllUseCase(StockJpaGateway stockJpaGateway) {
        this.stockJpaGateway = stockJpaGateway;
    }

    public Page<Stock> execute(Pageable pageable) {
        return stockJpaGateway.findAll(pageable);
    }
}

