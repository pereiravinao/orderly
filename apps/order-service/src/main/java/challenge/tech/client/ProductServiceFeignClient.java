package challenge.tech.client;

import challenge.tech.dto.presenter.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${clients.product-service.url}")
public interface ProductServiceFeignClient {

    @GetMapping("/v1/products/{id}")
    Product findById(@PathVariable("id") Long id);

    @GetMapping("/v1/products/sku/{sku}")
    Product findBySku(@PathVariable("sku") String sku);
}
