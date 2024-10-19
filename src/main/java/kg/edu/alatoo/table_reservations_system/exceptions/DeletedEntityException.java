package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class DeletedEntityException extends BaseException {
  private static final String DEFAULT_MESSAGE = "Entity deleted, no possibility to act with";

  private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

  public DeletedEntityException() {
    super(DEFAULT_MESSAGE, DEFAULT_STATUS);
  }

  public DeletedEntityException(String message) {
    super(message);
    super.httpStatus = DEFAULT_STATUS;
  }
}
