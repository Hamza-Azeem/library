package com.hamza.librarymanagementsystem.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRegistrationRequest( @NotNull(message = "Title can't be empty.") @NotEmpty(message = "Title can't be empty.") String title,
                                      @NotNull(message = "Author name can't be empty.") @NotEmpty(message = "Author name can't be empty.") String author,
                                      @NotNull(message = "Must provide a publication year") Integer publicationYear,
                                      @NotNull(message = "Must provide ISBN") @NotEmpty(message = "Must provide ISBN")
                                       @Size(min = 3, max = 13) String ISBN) {
}
