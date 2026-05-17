package dio.budgeting.infrastructure.http.request;

import lombok.NonNull;

public record LoginRequest(
        @NonNull String userName,
        @NonNull String password
) {
}
