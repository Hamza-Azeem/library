package com.hamza.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordNotModifiedException extends RuntimeException{
    public RecordNotModifiedException(String message) {
        super(message);
    }
}
