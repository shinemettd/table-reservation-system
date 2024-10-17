package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class TableAlreadyExists extends BaseException {

    private static final String DEFAULT_MESSAGE = "Table number already exists";

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

    public TableAlreadyExists() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public TableAlreadyExists(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}

