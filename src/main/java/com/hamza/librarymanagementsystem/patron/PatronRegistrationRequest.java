package com.hamza.librarymanagementsystem.patron;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PatronRegistrationRequest(
        @NotNull(message = "Name can't be empty.") @NotEmpty(message = "Name can't be empty.") String name,
        @NotNull(message = "Email can't be empty.") @NotEmpty(message = "Email can't be empty.") String email,
        @NotNull(message = "Phone number can't be empty.") @NotEmpty(message = "Phone number can't be empty.") String phoneNumber,
        @NotNull(message = "Address can't be empty.") @NotEmpty(message = "Address can't be empty.") String address
) {
}
