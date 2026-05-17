package dio.budgeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private UserId id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.id = new UserId();
        this.username = username;
        this.password = password;
    }
}
