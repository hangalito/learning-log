package dev.hangalito.topics.exceptions;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Component;

@Component
public class UserExistsException extends PersistenceException {

    public UserExistsException(String msg) {
        super(msg);
    }

    public UserExistsException() {
        super("Username already exists");
    }

}
