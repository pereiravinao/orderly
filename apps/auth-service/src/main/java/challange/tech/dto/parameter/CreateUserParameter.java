package challange.tech.dto.parameter;

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
}
