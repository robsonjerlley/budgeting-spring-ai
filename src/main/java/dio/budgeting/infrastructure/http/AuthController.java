package dio.budgeting.infrastructure.http;

import dio.budgeting.domain.UserRepository;
import dio.budgeting.infrastructure.http.request.LoginRequest;
import dio.budgeting.infrastructure.http.response.TokenResponse;
import dio.budgeting.infrastructure.identity.provider.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByUsername(loginRequest.userName())
                .filter(user -> user.getPassword().equals(loginRequest.password()))
                .map(user -> {
                    var token = jwtTokenProvider.generate(user.getUsername());
                    return ResponseEntity.ok(new TokenResponse(user.getUsername(), token));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
