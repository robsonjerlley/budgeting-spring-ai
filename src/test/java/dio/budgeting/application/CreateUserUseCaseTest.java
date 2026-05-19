package dio.budgeting.application;

import dio.budgeting.application.input.CreateUserInput;
import dio.budgeting.domain.User;
import dio.budgeting.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCase useCase;

    @Test
    @DisplayName("Deve criar usuário com senha codificada e retornar output")
    void shouldCreateUser() {
        var input = new CreateUserInput("joao", "senha123");
        var encodedPassword = "senha-codificada";
        var savedUser = new User("joao", encodedPassword);

        when(passwordEncoder.encode("senha123")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var output = useCase.execute(input);

        assertNotNull(output);
        assertEquals("joao", output.username());
        assertNotNull(output.id());

        verify(passwordEncoder).encode("senha123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve usar a senha codificada ao salvar o usuário")
    void shouldEncodePasswordBeforeSaving() {
        var input = new CreateUserInput("maria", "minhasenha");

        when(passwordEncoder.encode("minhasenha")).thenReturn("hash-da-senha");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(input);

        verify(passwordEncoder).encode(eq("minhasenha"));
        verify(userRepository).save(argThat(user -> user.getPassword().equals("hash-da-senha")));
    }
}
