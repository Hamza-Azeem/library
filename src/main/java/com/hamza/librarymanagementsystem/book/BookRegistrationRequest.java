package com.hamza.librarymanagementsystem.book;

public record BookRegistrationRequest(String title,
                               String author,
                               int publicationYear,
                               String ISBN) {
}
