package dio.budgeting.infrastructure.http.request;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record TransactionRequest(
        @NotBlank String description,
        @NonNull Category category,
        long amount) {


    public PersistTransactionInput toInput(String userName) {
        return new PersistTransactionInput(userName,description, amount, category);
    }
}
