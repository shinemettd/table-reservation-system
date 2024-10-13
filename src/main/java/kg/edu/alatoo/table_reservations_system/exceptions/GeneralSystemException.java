package kg.edu.alatoo.table_reservations_system.exceptions;

import org.springframework.http.HttpStatus;

public class GeneralSystemException extends BaseException {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.I_AM_A_TEAPOT;

    public GeneralSystemException(String message) {
        super(message);
        super.httpStatus = DEFAULT_STATUS;
    }
}
