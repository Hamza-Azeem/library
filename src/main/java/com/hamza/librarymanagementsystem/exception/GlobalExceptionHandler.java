package com.hamza.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Error> recordNotFoundExceptionHandler(RecordNotFoundException exception) {
        Error error = new Error(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordNotModifiedException.class)
    public ResponseEntity<Error> recordNotModifiedExceptionHandler(RecordNotModifiedException exception) {
        Error error = new Error(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Error> duplicateRecordExceptionHandler(DuplicateRecordException exception) {
        Error error = new Error(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        if (exception.getErrorCount() > 0) {
            for (ObjectError error : exception.getBindingResult().getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
        }
        Error error = new Error(
                errors.get(0),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
