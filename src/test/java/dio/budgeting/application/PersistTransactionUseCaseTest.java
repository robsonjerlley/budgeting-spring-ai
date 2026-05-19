package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersistTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PersistTransactionUseCase useCase;

    @Test
    @DisplayName("Deve persistir transação e retornar output mapeado")
    void shouldPersistTransaction() {
        var input = new PersistTransactionInput("joao", "Compra no mercado", 5000L, Category.GROCERIES);
        var saved = new Transaction("joao", "Compra no mercado", 5000L, Category.GROCERIES);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(saved);

        var output = useCase.execute(input);

        assertNotNull(output);
        assertEquals("Compra no mercado", output.description());
        assertEquals("GROCERIES", output.category());
        assertEquals(5000.00, output.value());
        assertNotNull(output.id());

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Deve mapear os dados do input para a transação salva")
    void shouldMapInputFieldsToTransaction() {
        var input = new PersistTransactionInput("maria", "Remédio", 2500L, Category.PHARMA);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(input);

        verify(transactionRepository).save(argThat(t ->
                t.getUserName().equals("maria") &&
                t.getDescription().equals("Remédio") &&
                t.getAmount() == 2500L &&
                t.getCategory() == Category.PHARMA
        ));
    }
}
