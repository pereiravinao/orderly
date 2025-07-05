package challenge.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
class OrderReceiverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderReceiverServiceApplication.class, args);
    }

}
