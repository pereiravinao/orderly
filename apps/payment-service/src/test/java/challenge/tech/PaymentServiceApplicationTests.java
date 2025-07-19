
package challenge.tech;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import challenge.tech.services.JwtTokenService;

@SpringBootTest
class PaymentServiceApplicationTests {

    @MockBean
    private JwtTokenService jwtTokenService;

    @Test
    void contextLoads() {
    }

}
