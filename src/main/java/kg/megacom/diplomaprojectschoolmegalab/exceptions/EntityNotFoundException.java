package kg.megacom.diplomaprojectschoolmegalab.exceptions;

import jakarta.persistence.EntityManager;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
