
package challenge.tech;

import challenge.tech.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class PaymentServiceApplicationTests {

    @MockitoBean
    private JwtTokenService jwtTokenService;

    @Test
    void contextLoads() {
    }

}
