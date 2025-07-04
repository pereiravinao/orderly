package challenge.tech.domain;

import challenge.tech.enums.UserRole;
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
public class UserAuth {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String cpf;
    private LocalDateTime createdAt;
    private Set<UserRole> roles = Set.of(UserRole.USER);

    private String password;
    private String token;

}
