package dio.budgeting.infrastructure.http.request;

import dio.budgeting.application.input.AuthenticateUserUseCaseInput;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String userName,
        @NotBlank String password
) {

  public  AuthenticateUserUseCaseInput toInput(){
        return new AuthenticateUserUseCaseInput(userName, password);
    }

}
