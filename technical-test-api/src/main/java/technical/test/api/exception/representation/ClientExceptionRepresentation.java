package technical.test.api.exception.representation;

public record ClientExceptionRepresentation(
    String errorLabel,
    String element,
    String message
) {

}
