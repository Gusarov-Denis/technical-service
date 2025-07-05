package org.example.oil.serviceExceptions;

public class ServiceNotFoundRuntimeException extends RuntimeException {
    public ServiceNotFoundRuntimeException(String message) {
        super(message);
    }
}
