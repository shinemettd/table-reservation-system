package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class ReservationAlreadyExists extends BaseException {
  private static final String DEFAULT_MESSAGE = "Reservation already exist";

  private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

  public ReservationAlreadyExists() {
    super(DEFAULT_MESSAGE, DEFAULT_STATUS);
  }

  public ReservationAlreadyExists(String message) {
    super(message);
    super.httpStatus = DEFAULT_STATUS;
  }

}
