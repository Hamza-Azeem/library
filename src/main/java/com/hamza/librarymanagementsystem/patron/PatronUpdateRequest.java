package com.hamza.librarymanagementsystem.patron;

public record PatronUpdateRequest(
        String name,
        String email,
        String phoneNumber,
        String address
) {
}
