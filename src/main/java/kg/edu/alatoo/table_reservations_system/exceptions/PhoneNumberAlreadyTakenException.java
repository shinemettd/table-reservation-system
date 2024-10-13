package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class PhoneNumberAlreadyTakenException extends BaseException {

    private static final String DEFAULT_MESSAGE = "Phone number already taken";

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

    public PhoneNumberAlreadyTakenException() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public PhoneNumberAlreadyTakenException(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}
