package org.example.springbootservices.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidEnumValueException extends RuntimeException {

    private final String field;

    public InvalidEnumValueException(String field, String message) {
        super(message);
        this.field = field;
    }
}
