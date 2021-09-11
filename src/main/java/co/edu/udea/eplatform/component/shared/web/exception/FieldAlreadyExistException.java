package co.edu.udea.eplatform.component.shared.web.exception;

public class FieldAlreadyExistException extends BadRequestException {

    private static final String DESCRIPTION = "Field Invalid Exception";

    public FieldAlreadyExistException(String detail){
        super(DESCRIPTION + " ." + detail);
    }
}
