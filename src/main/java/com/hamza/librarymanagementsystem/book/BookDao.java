package com.hamza.librarymanagementsystem.book;

import java.util.List;
import java.util.Optional;

interface BookDao {
    List<Book> selectAllBooks();
    Optional<Book> selectBookById(long id);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBookById(long id);
    boolean bookExistsWithIsbn(String isbn);
}
