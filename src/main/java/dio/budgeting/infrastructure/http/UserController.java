package dio.budgeting.infrastructure.http;

import dio.budgeting.application.CreateUserUseCase;
import dio.budgeting.infrastructure.http.request.UserRequest;
import dio.budgeting.infrastructure.http.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest request) {
        return UserResponse.from(createUserUseCase.execute(request.toInput()));
    }
}
