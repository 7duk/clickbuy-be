package dev.sideproject.ndx.exception;

public class RabbitMqException extends RuntimeException{
    public RabbitMqException(Throwable e) {
        super(e);
    }
}
