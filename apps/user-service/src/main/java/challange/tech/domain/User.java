package challange.tech.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class User {
    private Long id;
    private Long authId;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private LocalDateTime createdAt;
    private List<Address> addresses;

    public void update(User user) {
        if (user == null) return;

        if (user.getName() != null) {
            this.name = user.getName();
        }
        if (user.getPhone() != null) {
            this.phone = user.getPhone();
        }
    }
}
