package cloud.metaapi.sdk.clients.error_handler;

import cloud.metaapi.sdk.clients.error_handler.ApiException;

/**
 * Represents conflict error. Throwing this error results in 409 (Conflict) HTTP response code.
 */
public class ConflictException extends ApiException {

  private static final long serialVersionUID = 1L;
  
  /**
   * Constructs conflict error.
   * @param message error message
   */
  public ConflictException(String message) {
    super(message, 409);
  }
}
