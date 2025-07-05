package org.example.car.serviceExceptions;

public class ServiceNotFoundRuntimeException extends RuntimeException {
    public ServiceNotFoundRuntimeException(String message) {
        super(message);
    }
}
