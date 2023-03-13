package com.morotech.javachallenge.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.morotech.javachallenge.utils.MoroConstant.GENERIC_ERROR;

@ControllerAdvice
public class MoroExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(MoroBadRequestException.class)
    public ResponseEntity<MoroResponseError> handleBadRequest(MoroBadRequestException e) {
        LOGGER.error(e.getMessage());

        MoroResponseError response = new MoroResponseError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MoroResponseError> handleGenericException(Exception e) {
        LOGGER.error(e.getMessage());

        String error = GENERIC_ERROR + e.getMessage();

        MoroResponseError responseError = new MoroResponseError(error);

        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();

                    errors.put(fieldName, message);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
