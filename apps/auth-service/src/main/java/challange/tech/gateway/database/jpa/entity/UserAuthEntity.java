package challange.tech.gateway.database.jpa.entity;

import challange.tech.domain.UserAuth;
import challange.tech.enums.UserRole;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tb_user_auth")
@NoArgsConstructor
public class UserAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String cpf;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Set<UserRole> roles;

    @CreatedDate
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public UserAuthEntity(UserAuth userAuth) {
        this.id = userAuth.getId();
        this.email = userAuth.getEmail();
        this.password = userAuth.getPassword();
        this.cpf = userAuth.getCpf();
        this.roles = userAuth.getRoles();
        this.createdAt = userAuth.getCreatedAt();
    }


    public UserAuth toDomain() {
        return UserAuth.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .cpf(this.cpf)
                .roles(this.roles)
                .createdAt(this.createdAt)
                .build();
    }

}