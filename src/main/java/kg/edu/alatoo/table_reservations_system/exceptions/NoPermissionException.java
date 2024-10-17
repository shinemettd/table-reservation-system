package kg.edu.alatoo.table_reservations_system.exceptions;


import org.springframework.http.HttpStatus;

public class NoPermissionException extends BaseException {

    private static final String DEFAULT_MESSAGE = "You have no permission for that";

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.FORBIDDEN;

    public NoPermissionException() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public NoPermissionException(String message) {
        super(message);
    }

}
