package kg.edu.alatoo.table_reservations_system.exceptions.handle;

import kg.edu.alatoo.table_reservations_system.exceptions.*;
import kg.edu.alatoo.table_reservations_system.payload.errors.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseExceptions(BaseException e) {
        return this.createResponseEntityWithBaseException(e);
    }

    private <E extends BaseException> ErrorResponseDTO mapBaseExceptionToDTO(E e) {
        return new ErrorResponseDTO(List.of(e.getMessage()), LocalDateTime.now(), e.getHttpStatus().value());
    }

    private <E extends BaseException> ResponseEntity<ErrorResponseDTO> createResponseEntityWithBaseException(E e) {
        return new ResponseEntity<>(this.mapBaseExceptionToDTO(e), e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return this.createResponseEntityWithMethodArgumentNotValidException(e);
    }

    private ResponseEntity<ErrorResponseDTO> createResponseEntityWithMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
        return new ResponseEntity<>(new ErrorResponseDTO(errors, LocalDateTime.now(), e.getStatusCode().value()), e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return createResponseEntityWithException(e);
    }

    private <E extends Exception> ErrorResponseDTO mapExceptionToDTO(E e) {
        return new ErrorResponseDTO(List.of(e.getLocalizedMessage()), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private <E extends Exception> ResponseEntity<ErrorResponseDTO> createResponseEntityWithException(E e) {
        return new ResponseEntity<>(this.mapExceptionToDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
