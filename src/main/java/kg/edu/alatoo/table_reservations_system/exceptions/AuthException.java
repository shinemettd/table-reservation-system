package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class AuthException extends BaseException {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

    public AuthException(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}
