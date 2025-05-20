package com.example.userSubscription.exception;

import com.example.userSubscription.exception.app.AlreadySubscribedException;
import com.example.userSubscription.exception.app.SubscriptionNotFoundException;
import com.example.userSubscription.exception.app.UserAlreadyExistsException;
import com.example.userSubscription.exception.app.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.userSubscription.exception.ExceptionMessage.exceptionMessage;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            UserNotFoundException.class,
            SubscriptionNotFoundException.class,
    })
    public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionMessage exceptionMessage = exceptionMessage(e, httpStatus.value(), request);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            UserAlreadyExistsException.class,
    })
    public ResponseEntity<Object> handleBadRequestExceptions(RuntimeException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = exceptionMessage(e, httpStatus.value(), request);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            AlreadySubscribedException.class
    })
    public ResponseEntity<Object> handleConflictExceptions(RuntimeException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ExceptionMessage exceptionMessage = exceptionMessage(e, httpStatus.value(), request);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

}
