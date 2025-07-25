package challenge.tech;

import challenge.tech.client.PaymentServiceFeignClient;
import challenge.tech.client.ProductServiceFeignClient;
import challenge.tech.client.StockServiceFeignClient;
import challenge.tech.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class OrderServiceApplicationTest {

    @MockitoBean
    private JwtTokenService jwtTokenService;
    
    @MockitoBean
    private PaymentServiceFeignClient paymentServiceFeignClient;
    
    @MockitoBean
    private ProductServiceFeignClient productServiceFeignClient;
    
    @MockitoBean
    private StockServiceFeignClient stockServiceFeignClient;

    @Test
    void contextLoads() {
        // This test simply checks if the Spring application context loads successfully.
        // If it loads without exceptions, the test passes.
    }
}
