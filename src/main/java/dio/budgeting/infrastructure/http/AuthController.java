package dio.budgeting.infrastructure.http;

import dio.budgeting.application.AuthenticationUserUseCase;
import dio.budgeting.infrastructure.http.request.LoginRequest;
import dio.budgeting.infrastructure.http.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationUserUseCase authenticationUserUseCase;

    public AuthController(AuthenticationUserUseCase authenticationUserUseCase) {
        this.authenticationUserUseCase = authenticationUserUseCase;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Validated LoginRequest loginRequest) {

        var output = authenticationUserUseCase.execute(loginRequest.toInput());

        return ResponseEntity.ok(new TokenResponse(output.userName(), output.token()));
    }
}
