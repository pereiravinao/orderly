package challange.tech.service;

import challange.tech.client.ProductServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductValidationService {

    private final ProductServiceFeignClient productServiceFeignClient;

    public void validateProductExistence(Long productId) {
        productServiceFeignClient.findById(productId);
    }
}