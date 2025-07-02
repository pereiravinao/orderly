package challange.tech;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

    @Test
    @DisplayName("Deve carregar o contexto da aplicação")
    @Description("Necessita do banco de dados rodando.")
    void contextLoads() {
        AuthServiceApplication.main(new String[]{});
    }
}
