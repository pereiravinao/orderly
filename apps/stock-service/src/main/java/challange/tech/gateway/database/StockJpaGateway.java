package challange.tech.gateway.database;

import challange.tech.domain.Stock;
import challange.tech.exceptions.stock.StockExceptionHandler;
import challange.tech.gateway.database.jpa.entity.StockEntity;
import challange.tech.gateway.database.jpa.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockJpaGateway {

    private final StockRepository stockRepository;

    public Page<Stock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable).map(StockEntity::toDomain);
    }

    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .map(StockEntity::toDomain)
                .orElseThrow(StockExceptionHandler::notFound);
    }

    public Stock save(Stock stock) {
        return stockRepository.save(new StockEntity(stock)).toDomain();
    }

    public void deleteById(Long id) {
        stockRepository.deleteById(id);
    }

    public Optional<Stock> findByProductId(Long productId) {
        return stockRepository.findByProductId(productId)
                .map(StockEntity::toDomain);
    }

}

