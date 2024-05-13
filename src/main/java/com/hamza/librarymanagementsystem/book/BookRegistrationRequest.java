package com.hamza.librarymanagementsystem.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRegistrationRequest(@NotNull @NotEmpty String title,
                                      @NotNull @NotEmpty String author,
                                      @NotNull @NotEmpty Integer publicationYear,
                                      @NotNull @NotEmpty String ISBN) {
}
