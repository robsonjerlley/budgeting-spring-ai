package dio.budgeting.infrastructure.http.request;

import dio.budgeting.application.input.CreateUserInput;

public record UserRequest(String username, String password) {
    public CreateUserInput toInput() {
        return new CreateUserInput(username, password);
    }
}
