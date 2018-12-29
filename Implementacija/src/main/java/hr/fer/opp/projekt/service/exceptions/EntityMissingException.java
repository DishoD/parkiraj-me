package hr.fer.opp.projekt.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityMissingException extends RuntimeException {
    public EntityMissingException(Class<?> cls, Object ref) {
        super("Entitet s referencom " + ref + " od " + cls + " nije pronaden.");
    }
}
