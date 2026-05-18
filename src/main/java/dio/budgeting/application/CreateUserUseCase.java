package dio.budgeting.application;

import dio.budgeting.application.input.CreateUserInput;
import dio.budgeting.application.output.UserOutput;
import dio.budgeting.domain.User;
import dio.budgeting.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutput execute(CreateUserInput input) {
        var user = new User(input.username(), passwordEncoder.encode(input.password()));

        return UserOutput.from(userRepository.save(user));
    }
}
