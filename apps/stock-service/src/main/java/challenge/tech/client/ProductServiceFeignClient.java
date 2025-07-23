package challenge.tech.client;

import challenge.tech.client.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${clients.product-service.url}")
public interface ProductServiceFeignClient {

    @GetMapping("/v1/products/{id}")
    ProductDto findById(@PathVariable("id") Long id);
}