package co.edu.udea.eplatform.component.shared.web.exception;

public class ConflictException extends RuntimeException {

    private static final long serialVersionUID = -5644568184780083105L;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
