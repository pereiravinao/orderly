package challange.tech.gateway.database.jpa.entity;


import challange.tech.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authId;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<AddressEntity> addresses;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public User toDomain() {
        User user = new User();
        user.setId(this.id);
        user.setAuthId(this.authId);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setCpf(this.cpf);
        user.setPhone(this.phone);
        user.setCreatedAt(this.createdAt);
        if (Objects.nonNull(this.addresses)) {
            user.setAddresses(this.addresses.stream().map(AddressEntity::toDomain).toList());
        }
        return user;
    }

    public UserEntity(User user) {
        this.id = user.getId();
        this.authId = user.getAuthId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
        if (Objects.nonNull(user.getAddresses())) {
            this.addresses = user.getAddresses().stream().map(AddressEntity::new).toList();
        }
    }

}