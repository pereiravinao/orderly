package challange.tech.usecase;

import challange.tech.gateway.database.StockJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUseCase {
    private final StockJpaGateway stockJpaGateway;

    public void execute(Long id) {
        stockJpaGateway.deleteById(id);
    }
}

