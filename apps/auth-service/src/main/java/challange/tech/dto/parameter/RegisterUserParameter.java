package challange.tech.dto.parameter;

import challange.tech.domain.UserAuth;
import challange.tech.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class RegisterUserParameter {
    @NotEmpty(message = "Nome é obrigatório")
    private String name;

    @NotEmpty(message = "Telefone é obrigatório")
    private String phone;

    @NotEmpty(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "Senha é obrigatória")
    @Size(min = 6, max = 16, message = "Senha deve ter entre 6 e 16 caracteres")
    private String password;

    @NotEmpty(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    private String cpf;

    public UserAuth toDomain() {
        return UserAuth.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .password(password)
                .cpf(cpf)
                .roles(Set.of(UserRole.USER))
                .build();
    }
}
