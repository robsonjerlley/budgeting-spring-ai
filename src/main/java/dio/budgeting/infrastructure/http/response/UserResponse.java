package dio.budgeting.infrastructure.http.response;

import dio.budgeting.application.output.UserOutput;

public record UserResponse(String id, String username) {
    public static UserResponse from(UserOutput output) {
        return new UserResponse(output.id(), output.username());
    }
}
