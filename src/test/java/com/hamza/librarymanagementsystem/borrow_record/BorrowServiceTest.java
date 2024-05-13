package com.hamza.librarymanagementsystem.borrow_record;

import com.hamza.librarymanagementsystem.book.Book;
import com.hamza.librarymanagementsystem.book.BookService;
import com.hamza.librarymanagementsystem.exception.DuplicateRecordException;
import com.hamza.librarymanagementsystem.exception.RecordNotFoundException;
import com.hamza.librarymanagementsystem.patron.Patron;
import com.hamza.librarymanagementsystem.patron.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {
    @Mock
    private BorrowRecordDao borrowRecordDao;
    @Mock
    private PatronService patronService;
    @Mock
    private BookService bookService;
    private BorrowRecordService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BorrowRecordService(borrowRecordDao, bookService, patronService);
    }

    @Test
    void borrowBook() {
        // Arrange
        long patronId = 1, bookId = 1;
        when(patronService.findPatronById(patronId)).thenReturn(new Patron());
        when(bookService.findBookById(bookId)).thenReturn(new Book());
        when(borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId))
                .thenReturn(Optional.empty());
        // Act
        underTest.borrowBook(patronId, bookId);
        // Assert
        verify(borrowRecordDao).borrowBook(patronId, bookId);
    }
    @Test
    void borrowBookWillThrowExceptionWhenBookHasBeenBorrowedBefore() {
        // Arrange
        long patronId = 1, bookId = 1;
        when(patronService.findPatronById(patronId)).thenReturn(new Patron());
        when(bookService.findBookById(bookId)).thenReturn(new Book());
        when(borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId))
                .thenReturn(Optional.of(new BorrowRecord()));

        // Assert
        assertThatThrownBy(() -> underTest.borrowBook(patronId, bookId))
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("You already borrowed this book before!");
    }

    @Test
    void returnBook() {
        // Arrange
        long patronId = 1, bookId = 1;
        BorrowRecord borrowRecord = new BorrowRecord(
                patronId,
                bookId,
                Date.valueOf(LocalDate.now()),
                null
        );
        when(patronService.findPatronById(patronId)).thenReturn(new Patron());
        when(bookService.findBookById(bookId)).thenReturn(new Book());
        when(borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId))
                .thenReturn(Optional.of(borrowRecord));
        // Act
        underTest.returnBook(patronId, bookId);
        // Assert
        verify(borrowRecordDao).returnBook(borrowRecord);
    }
    @Test
    void returnBookWillThrowExceptionWhenReturnDateIsNotNull() {
        // Arrange
        long patronId = 1, bookId = 1;
        BorrowRecord borrowRecord = new BorrowRecord(
                patronId,
                bookId,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now())
        );
        when(patronService.findPatronById(patronId)).thenReturn(new Patron());
        when(bookService.findBookById(bookId)).thenReturn(new Book());
        when(borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId))
                .thenReturn(Optional.of(borrowRecord));
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.returnBook(patronId, bookId))
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("You already returned this book at: %s", borrowRecord.getReturnDate()
                ));
    }
    @Test
    void returnBookWillThrowExceptionWhenBorrowRecordIsNotFound() {
        // Arrange
        long patronId = 1, bookId = 1;

        when(patronService.findPatronById(patronId)).thenReturn(new Patron());
        when(bookService.findBookById(bookId)).thenReturn(new Book());
        when(borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId))
                .thenReturn(Optional.empty());
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.returnBook(patronId, bookId))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("You didn't borrow a book with id=%s", bookId));
    }
}