package dio.budgeting.application;

import dio.budgeting.application.exception.InvalidCredentialsException;
import dio.budgeting.application.input.AuthenticateUserUseCaseInput;
import dio.budgeting.application.output.TokenOutput;
import dio.budgeting.domain.UserRepository;
import dio.budgeting.infrastructure.identity.provider.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserUseCase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationUserUseCase(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    public TokenOutput execute(AuthenticateUserUseCaseInput input) {


        var user = userRepository.findByUsername(input.userName())
                .filter(u -> passwordEncoder.matches(input.password(), u.getPassword()))
                .orElseThrow(InvalidCredentialsException::new);

        var token = jwtTokenProvider.generate(user.getUsername());

        return new TokenOutput(user.getUsername(), token);
    }


}
