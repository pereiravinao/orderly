package challange.tech.usecase;

import challange.tech.domain.Stock;
import challange.tech.gateway.database.StockJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdUseCase {
    private final StockJpaGateway stockJpaGateway;

    public Stock execute(Long id) {
        return stockJpaGateway.findById(id);
    }
}

