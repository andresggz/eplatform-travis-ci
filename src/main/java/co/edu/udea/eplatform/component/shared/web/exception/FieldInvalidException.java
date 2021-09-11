package co.edu.udea.eplatform.component.shared.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FieldInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FieldInvalidException(final String message) {
        super(message);
    }

    public FieldInvalidException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
