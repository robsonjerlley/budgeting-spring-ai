package dio.budgeting.application;

import dio.budgeting.application.input.AuthenticateUserUseCaseInput;
import dio.budgeting.application.output.TokenOutput;
import dio.budgeting.domain.UserRepository;
import dio.budgeting.infrastructure.identity.provider.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserUseCase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthenticationUserUseCase(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public TokenOutput execute(AuthenticateUserUseCaseInput input) {


        var user = userRepository.findByUsername(input.userName())
                .filter(u -> u.getPassword().equals(input.password()))
                .orElseThrow();

        var token = jwtTokenProvider.generate(user.getUsername());

        return new TokenOutput(user.getUsername(), token);
    }


}
