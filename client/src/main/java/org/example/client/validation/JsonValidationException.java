package org.example.client.validation;

public class JsonValidationException extends RuntimeException {
    public JsonValidationException(String message) {
        super(message);
    }
}
