package org.example.serv.serviceExceptions;

public class ServiceNotFoundRuntimeException extends RuntimeException {
    public ServiceNotFoundRuntimeException(String message) {
        super(message);
    }
}
