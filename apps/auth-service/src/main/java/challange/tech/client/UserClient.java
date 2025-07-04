package challange.tech.client;

import challange.tech.dto.parameter.CreateUserParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${clients.user-service.url}")
public interface UserClient {

    @PostMapping("/v1/users")
    void createUser(@RequestBody CreateUserParameter userParameter);

}
