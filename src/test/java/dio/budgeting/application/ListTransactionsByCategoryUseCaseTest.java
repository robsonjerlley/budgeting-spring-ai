package dio.budgeting.application;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListTransactionsByCategoryUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ListTransactionsByCategoryUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista de transações mapeadas para a categoria informada")
    void shouldListTransactionsByCategory() {
        var category = Category.GROCERIES;
        var transactions = List.of(
                new Transaction("joao", "Feira", 3000L, Category.GROCERIES),
                new Transaction("maria", "Supermercado", 7500L, Category.GROCERIES)
        );

        when(transactionRepository.findAllByCategory(category)).thenReturn(transactions);

        var output = useCase.execute(category);

        assertNotNull(output);
        assertEquals(2, output.size());
        assertTrue(output.stream().allMatch(o -> o.category().equals("GROCERIES")));

        verify(transactionRepository).findAllByCategory(category);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há transações na categoria")
    void shouldReturnEmptyListWhenNoTransactions() {
        when(transactionRepository.findAllByCategory(Category.AUTO)).thenReturn(List.of());

        var output = useCase.execute(Category.AUTO);

        assertNotNull(output);
        assertTrue(output.isEmpty());
    }

    @Test
    @DisplayName("Deve mapear corretamente os campos de cada transação")
    void shouldMapTransactionFieldsCorrectly() {
        var transaction = new Transaction("joao", "Posto", 10000L, Category.AUTO);

        when(transactionRepository.findAllByCategory(Category.AUTO)).thenReturn(List.of(transaction));

        var output = useCase.execute(Category.AUTO);

        assertEquals(1, output.size());
        var item = output.getFirst();
        assertEquals("Posto", item.description());
        assertEquals("AUTO", item.category());
        assertEquals(10000.00, item.value());
        assertNotNull(item.id());
    }
}
