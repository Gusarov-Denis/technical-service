package org.example.client.serviceExceptions;

public class ServiceNotFoundRuntimeException extends RuntimeException {
    public ServiceNotFoundRuntimeException(String message) {
        super(message);
    }
}
