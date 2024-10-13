package kg.megacom.diplomaprojectschoolmegalab.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
}
