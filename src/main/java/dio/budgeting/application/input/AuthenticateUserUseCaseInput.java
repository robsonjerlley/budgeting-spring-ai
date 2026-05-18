package dio.budgeting.application.input;

public record AuthenticateUserUseCaseInput(
        String userName,
        String password
) {
}
