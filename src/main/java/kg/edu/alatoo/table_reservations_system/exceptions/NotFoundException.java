package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}
