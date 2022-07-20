package ru.practicum.shareit.exception;

import org.apache.logging.log4j.LoggingException;

public class DuplicateEmailException extends LoggingException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
