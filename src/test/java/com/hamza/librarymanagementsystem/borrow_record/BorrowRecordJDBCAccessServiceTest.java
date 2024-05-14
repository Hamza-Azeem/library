package com.hamza.librarymanagementsystem.borrow_record;

import com.github.javafaker.Faker;
import com.hamza.librarymanagementsystem.TestcontainersAbstraction;
import com.hamza.librarymanagementsystem.book.Book;
import com.hamza.librarymanagementsystem.book.BookJDBCDataAccessService;
import com.hamza.librarymanagementsystem.book.BookRowMapper;
import com.hamza.librarymanagementsystem.patron.Patron;
import com.hamza.librarymanagementsystem.patron.PatronJDBCAccessService;
import com.hamza.librarymanagementsystem.patron.PatronRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/*
        #######################################
        EACH TEST HAVE TO BE RUN INDEPENDENTLY.
        ######################################
 */
class BorrowRecordJDBCAccessServiceTest extends TestcontainersAbstraction {
//    private BorrowRecordJDBCAccessService underTest;
//    private PatronJDBCAccessService patronJDBCAccessService;
//    private BookJDBCDataAccessService bookJDBCDataAccessService;
//    private final Faker faker = new Faker();
//
//    private final BorrowRecordRowMapper borrowRecordRowMapper = new BorrowRecordRowMapper();
//    private final BookRowMapper bookRowMapper = new BookRowMapper();
//    private final PatronRowMapper patronRowMapper = new PatronRowMapper();
//    @BeforeEach
//    void setUp() {
//        underTest = new BorrowRecordJDBCAccessService(getJdbcTemplate(), borrowRecordRowMapper);
//        patronJDBCAccessService = new PatronJDBCAccessService(getJdbcTemplate(), patronRowMapper);
//        bookJDBCDataAccessService = new BookJDBCDataAccessService(getJdbcTemplate(), bookRowMapper);
//    }
//
//    @Test
//    void borrowBook() {
//        // Arrange
//        long patronId = 1, bookId=1;
//        String isbn = faker.idNumber().valid();
//        Book book = new Book(
//                "title",
//                "author",
//                2023,
//                isbn
//        );
//        bookJDBCDataAccessService.addBook(book);
//        String email = "email@gmail.com";
//        Patron patron = new Patron(
//                "name",
//                email,
//                "25235432",
//                "address"
//        );
//        patronJDBCAccessService.addPatron(patron);
//        // Act
//        underTest.borrowBook(patronId, bookId);
//        Optional<BorrowRecord> borrowRecord = underTest.selectAllRecords()
//                .stream()
//                .filter(b -> b.getBookId() == bookId && b.getPatronId() == patronId)
//                .findFirst();
//        // Assert
//        assertThat(borrowRecord).isNotEmpty();
//        assertThat(borrowRecord.get().getReturnDate()).isNull();
//    }
//
//    @Test
//    void returnBook() {
//        // Arrange
//        long patronId =1, bookId=1;
//        String isbn = faker.idNumber().valid();
//        Book book = new Book(
//                "title",
//                "author",
//                2023,
//                isbn
//        );
//        bookJDBCDataAccessService.addBook(book);
//        String email = "email@gmail.com";
//        Patron patron = new Patron(
//                "name",
//                email,
//                "25235432",
//                "address"
//        );
//        patronJDBCAccessService.addPatron(patron);
//        underTest.borrowBook(patronId, bookId);
//        BorrowRecord borrowRecord = underTest.selectAllRecords().stream()
//                .filter(b -> b.getBookId() == bookId && b.getPatronId() == patronId)
//                        .findFirst().orElseThrow();
//        // Act
//        underTest.returnBook(borrowRecord);
//        // Assert
//        assertThat(borrowRecord.getReturnDate()).isEqualTo(Date.valueOf(LocalDate.now()));
//    }
}