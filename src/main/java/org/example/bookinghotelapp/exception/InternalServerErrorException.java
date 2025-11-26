package org.example.bookinghotelapp.exception;

public class InternalServerErrorException extends CustomException {

    public InternalServerErrorException(String message) {
        super(message, 0);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, 0, cause);
    }

    public InternalServerErrorException(String message, Object... args) {
        super(message, 0, args);

    }

    @Override
    public InternalServerErrorException createCustomException(String message) {
        return new InternalServerErrorException(message);
    }
}
