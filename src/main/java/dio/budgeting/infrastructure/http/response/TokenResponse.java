package dio.budgeting.infrastructure.http.response;

public record TokenResponse(
        String userName,
        String token
) {
}
