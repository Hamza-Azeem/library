package com.hamza.librarymanagementsystem.book;

public record BookUpdateRequest(String title,
                               String author,
                               Integer publicationYear,
                               String ISBN) {
}

