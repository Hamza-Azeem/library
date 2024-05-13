package com.hamza.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record Error(
        String message,
        LocalDateTime timestamp,
        HttpStatus status
) {

}
