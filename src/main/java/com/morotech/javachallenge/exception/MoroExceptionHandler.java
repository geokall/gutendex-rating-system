package com.morotech.javachallenge.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MoroExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoroExceptionHandler.class);

    @ExceptionHandler(MoroNotFoundException.class)
    public ResponseEntity<MoroResponseError> handleNotFoundException(MoroNotFoundException e) {
        LOGGER.error(e.getMessage());

        MoroResponseError responseError = new MoroResponseError(e.getMessage());

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MoroUnauthorizedException.class)
    public ResponseEntity<MoroResponseError> handleUnauthorized(MoroUnauthorizedException e) {
        LOGGER.error(e.getMessage());

        MoroResponseError response = new MoroResponseError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MoroForbiddenException.class)
    public ResponseEntity<MoroResponseError> handleForbidden(MoroForbiddenException e) {
        LOGGER.error(e.getMessage());

        MoroResponseError response = new MoroResponseError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        LOGGER.error(e.getMessage());

        String error = "There was an error. Please contact your administrator " + e.getMessage();

        MoroResponseError responseError = new MoroResponseError(error);

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
