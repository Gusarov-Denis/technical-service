package org.example.serv.validation;

public class JsonValidationException extends RuntimeException {
    public JsonValidationException(String message) {
        super(message);
    }
}
