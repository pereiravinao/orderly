package challenge.tech.usecase;

import challenge.tech.gateway.database.OrderJpaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUseCase {
    private final OrderJpaGateway orderJpaGateway;

    public void execute(Long id) {
        orderJpaGateway.deleteById(id);
    }
}

