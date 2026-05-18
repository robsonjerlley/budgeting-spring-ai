package dio.budgeting.infrastructure.http.request;

import dio.budgeting.application.input.CreateUserInput;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String userName,
        @NotBlank String password
) {


    public CreateUserInput toInput() {

        return new CreateUserInput(userName, password);
    }
}
