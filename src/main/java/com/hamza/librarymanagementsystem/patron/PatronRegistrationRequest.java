package com.hamza.librarymanagementsystem.patron;

public record PatronRegistrationRequest(
        String name,
        String email,
        String phoneNumber,
        String address
) {
}
