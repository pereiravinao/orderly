package challenge.tech.dto.parameter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginParameter {

    @NotEmpty(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "Senha é obrigatória")
    @Size(min = 6, max = 16, message = "Senha deve ter entre 6 e 16 caracteres")
    private String password;
}
