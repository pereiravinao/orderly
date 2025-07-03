package challange.tech.dto.paremeter;

import challange.tech.domain.User;
import lombok.Getter;

@Getter
public class CreateUserParameter {
    private String name;
    private String phone;

    public User toDomain() {
        User user = new User();
        user.setName(this.name);
        user.setPhone(this.phone);
        return user;
    }

}
