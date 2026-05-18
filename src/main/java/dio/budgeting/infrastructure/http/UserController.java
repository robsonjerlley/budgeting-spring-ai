package dio.budgeting.infrastructure.http;

import dio.budgeting.application.CreateUserUseCase;
import dio.budgeting.infrastructure.http.request.UserRequest;
import dio.budgeting.infrastructure.http.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {

        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Validated UserRequest request) {
        var response = UserResponse.from(createUserUseCase.execute(request.toInput()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
