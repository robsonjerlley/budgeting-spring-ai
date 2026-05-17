package dio.budgeting.infrastructure.persistence.entity;

import dio.budgeting.domain.User;
import dio.budgeting.domain.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private UUID id;
    private String username;
    private String password;

    public static UserEntity from(User user) {
        return new UserEntity(user.getId().uuid(), user.getUsername(), user.getPassword());
    }

    public User toDomain() {

        return new User(new UserId(this.id), this.username, this.password);
    }
}
