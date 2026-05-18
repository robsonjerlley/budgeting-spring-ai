package dio.budgeting.application;

import dio.budgeting.application.input.CreateUserInput;
import dio.budgeting.application.output.UserOutput;
import dio.budgeting.domain.User;
import dio.budgeting.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public UserOutput execute(CreateUserInput input) {
        var user = new User(input.username(), input.password());

        return UserOutput.from(userRepository.save(user));
    }
}
