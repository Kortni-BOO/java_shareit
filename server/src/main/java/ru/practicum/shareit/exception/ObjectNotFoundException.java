package ru.practicum.shareit.exception;

import org.apache.logging.log4j.LoggingException;

public class ObjectNotFoundException extends LoggingException {
    public ObjectNotFoundException(String message) {
        super(message);
    }

}
