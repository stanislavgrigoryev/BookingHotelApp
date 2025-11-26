package org.example.bookinghotelapp.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookinghotelapp.controller.response.ExceptionResponse;
import org.example.bookinghotelapp.exception.BadRequestException;
import org.example.bookinghotelapp.exception.ConflictException;
import org.example.bookinghotelapp.exception.CustomException;
import org.example.bookinghotelapp.exception.InternalServerErrorException;
import org.example.bookinghotelapp.exception.NotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handle(NotFoundException e, Locale locale) {

        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(BadRequestException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(InternalServerErrorException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ExceptionResponse handle(ConflictException e, Locale locale) {
        return getExceptionResponse(e, locale);
    }

    private ExceptionResponse getExceptionResponse(Exception e, Locale locale, String placeHolder, Object... args) {
        log.info("Handling exception", e);
        return new ExceptionResponse(getMessage(placeHolder, locale, args));
    }

    private ExceptionResponse getExceptionResponse(CustomException e, Locale locale) {
        String message = getMessage(e.getMessage(), Locale.ENGLISH, e.getArgs());
        CustomException customException = e.createCustomException(message);
        customException.setStackTrace(e.getStackTrace());
        log.info("Handle exception: Message: %s".formatted(message), customException);
        return new ExceptionResponse(getMessage(customException.getMessage(), locale, e.getArgs()));

    }

    private String getMessage(String placeHolder, Locale locale, Object... args) {
        return messageSource.getMessage(placeHolder, args, locale);
    }
}
