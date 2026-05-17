package dio.budgeting.application.output;

import dio.budgeting.domain.User;

public record UserOutput(String id, String username) {
    public static UserOutput from(User user) {
        return new UserOutput(user.getId().uuid().toString(), user.getUsername());
    }
}
