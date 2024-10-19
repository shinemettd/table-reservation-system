package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class NotValidException extends BaseException {

    private static final String DEFAULT_MESSAGE = "Got not valid values";

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

    public NotValidException() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public NotValidException(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}

