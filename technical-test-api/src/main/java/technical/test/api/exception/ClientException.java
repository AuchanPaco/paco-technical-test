package technical.test.api.exception;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import technical.test.api.exception.representation.ClientExceptionRepresentation;

public class ClientException extends RuntimeException {

  final String element;

  public ClientException(final String element, final String message) {
    super(message);
    this.element = element;
  }

  public Mono<ServerResponse> getAsResponse() {
    return ServerResponse.badRequest()
                         .bodyValue(new ClientExceptionRepresentation(this.getClass().getSimpleName(),
                                                                      this.element,
                                                                      super.getMessage()));
  }

}
