package challange.tech.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("Administrator"),
    ROLE_MANAGER("Gerente"),
    ROLE_EMPLOYEE("Funcionário"),
    ROLE_CLIENT("Cliente"),
    ROLE_USER("Usuário");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
