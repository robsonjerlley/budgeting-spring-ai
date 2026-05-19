package dio.budgeting.application;

import dio.budgeting.application.exception.InvalidCredentialsException;
import dio.budgeting.application.input.AuthenticateUserUseCaseInput;
import dio.budgeting.domain.User;
import dio.budgeting.domain.UserRepository;
import dio.budgeting.infrastructure.identity.provider.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationUserUseCase useCase;

    @Test
    @DisplayName("Deve autenticar e retornar token quando credenciais são válidas")
    void shouldAuthenticateWithValidCredentials() {
        var input = new AuthenticateUserUseCaseInput("joao", "senha123");
        var user = new User("joao", "senha-hash");

        when(userRepository.findByUsername("joao")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha123", "senha-hash")).thenReturn(true);
        when(jwtTokenProvider.generate("joao")).thenReturn("jwt-token-gerado");

        var output = useCase.execute(input);

        assertNotNull(output);
        assertEquals("joao", output.userName());
        assertEquals("jwt-token-gerado", output.token());

        verify(jwtTokenProvider).generate("joao");
    }

    @Test
    @DisplayName("Deve lançar InvalidCredentialsException quando usuário não existe")
    void shouldThrowWhenUserNotFound() {
        var input = new AuthenticateUserUseCaseInput("inexistente", "qualquersenha");

        when(userRepository.findByUsername("inexistente")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(input));

        verifyNoInteractions(jwtTokenProvider);
    }

    @Test
    @DisplayName("Deve lançar InvalidCredentialsException quando senha não confere")
    void shouldThrowWhenPasswordDoesNotMatch() {
        var input = new AuthenticateUserUseCaseInput("joao", "senha-errada");
        var user = new User("joao", "senha-hash");

        when(userRepository.findByUsername("joao")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha-errada", "senha-hash")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(input));

        verifyNoInteractions(jwtTokenProvider);
    }
}
