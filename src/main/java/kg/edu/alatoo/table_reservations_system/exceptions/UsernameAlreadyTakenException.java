package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends BaseException {

    private static final String DEFAULT_MESSAGE = "Username already taken";

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

    public UsernameAlreadyTakenException() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public UsernameAlreadyTakenException(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}
