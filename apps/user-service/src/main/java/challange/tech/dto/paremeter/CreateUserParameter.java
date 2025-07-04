package challange.tech.dto.paremeter;

import challange.tech.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserParameter {
    private Long authId;
    private String name;
    private String phone;
    private String email;
    private String cpf;

    public User toDomain() {
        User user = new User();
        user.setAuthId(this.authId);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        user.setCpf(this.cpf);
        return user;
    }

}
