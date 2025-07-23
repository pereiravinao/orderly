package challenge.tech;

import challenge.tech.client.ProductServiceFeignClient;
import challenge.tech.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class StockServiceApplicationTest {

    @MockitoBean
    private JwtTokenService jwtTokenService;
    
    @MockitoBean
    private ProductServiceFeignClient productServiceFeignClient;

    @Test
    void contextLoads() {
        // This test simply checks if the Spring application context loads successfully.
        // If it loads without exceptions, the test passes.
    }
}