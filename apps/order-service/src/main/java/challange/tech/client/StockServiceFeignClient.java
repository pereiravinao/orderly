package challange.tech.client;

import challange.tech.client.dto.StockDto;
import challange.tech.client.dto.UpdateStockParameter;
import challange.tech.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stock-service", url = "${stock-service.url}", configuration = FeignConfig.class)
public interface StockServiceFeignClient {

    @GetMapping("/v1/stocks/products/{productId}")
    StockDto findByProductId(@PathVariable("productId") Long productId);

    @PutMapping("/v1/stocks/{id}")
    void update(@PathVariable("id") Long id, @RequestBody UpdateStockParameter parameter);
}