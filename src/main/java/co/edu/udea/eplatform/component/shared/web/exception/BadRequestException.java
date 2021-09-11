package co.edu.udea.eplatform.component.shared.web.exception;


public class BadRequestException extends RuntimeException {

    private static final String Description = "Conflict Exception (409)";

    public BadRequestException(String detail){
        super(Description + ". " + detail);
    }
}
