package com.hamza.librarymanagementsystem.patron;


import java.util.List;
import java.util.Optional;

public interface PatronDao {
    List<Patron> selectAllPatrons();
    Optional<Patron> selectPatronById(long id);
    void addPatron(Patron patron);
    void updatePatron(Patron patron);
    void deletePatronById(long id);
    boolean patronExistsWithEmail(String email);
}
