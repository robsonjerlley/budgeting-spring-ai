package dio.budgeting.infrastructure.identity.provider;


import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class JwtTokenProviderTest {

    private final JwtTokenProvider provider = new JwtTokenProvider(
            "chave-secreta-secreta-mesmo-ou-nao", 3600000
    );

    private final JwtTokenProvider expiredProvider = new JwtTokenProvider(
            "chave-secreta-secreta-mesmo-ou-nao", 1
    );

    @Test
    @DisplayName("Deve gerar um token com sucesso")
    void shouldGenerateToken() {

        var result = provider.generate("josesilva");

        assertNotNull(result);
        assertFalse(result.isBlank());
        System.out.println(result);
    }

    @Test
    @DisplayName("Deve extrair subject com sucesso")
    void shouldExtractSubject() {
      var token =  provider.generate("josesilva");

      var result = provider.extractSubject(token);

        assertNotNull(result);
        assertFalse(result.isBlank());
        assertEquals(result, "josesilva");
        System.out.println(result);
    }

    @SneakyThrows
    @Test
    @DisplayName("Deve validar token com sucesso")
    void shouldValidateToken(){

        var tokenValid =  provider.generate("josesilva");
        var result  =  provider.isValid(tokenValid);

        assertTrue(result);
        System.out.println(result);

        var tokenExpired =  expiredProvider.generate("josesilva");
        Thread.sleep(2);
        var resultExpired =  expiredProvider.isValid(tokenExpired);
        assertFalse(resultExpired);
        System.out.println(resultExpired);

        var noToken = "nao-valido";
        assertFalse(provider.isValid(noToken));

    }
}
