package challenge.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {
    private Long id;
    private String email;
    private String password;
    private String cpf;
    private LocalDateTime createdAt;
    private String token;
    private Set<String> roles;
}
