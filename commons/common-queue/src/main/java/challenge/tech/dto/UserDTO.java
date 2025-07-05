package challenge.tech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTO(Long id) {
        this.id = id;
    }
}
