package com.hamza.librarymanagementsystem.borrow_record;

import com.hamza.librarymanagementsystem.book.BookService;
import com.hamza.librarymanagementsystem.exception.DuplicateRecordException;
import com.hamza.librarymanagementsystem.exception.RecordNotFoundException;
import com.hamza.librarymanagementsystem.patron.PatronService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowRecordService {
    private final BorrowRecordDao borrowRecordDao;
    private final BookService bookService;
    private final PatronService patronService;

    public BorrowRecordService(BorrowRecordDao borrowRecordDao, BookService bookService, PatronService patronService) {
        this.borrowRecordDao = borrowRecordDao;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    public void borrowBook(long patronId, long bookId){
        // Should implement both patronExistsById and bookExistsById
        patronService.findPatronById(patronId); // check if patron exists
        bookService.findBookById(bookId);       // check if book exists
        Optional<BorrowRecord> borrowRecordOptional =
                borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId);
        if(borrowRecordOptional.isPresent()){
            throw new DuplicateRecordException("You already borrowed this book before!");
        }
        borrowRecordDao.borrowBook(patronId, bookId);
    }
    public void returnBook(long patronId, long bookId){
        // Should implement both patronExistsById and bookExistsById
        patronService.findPatronById(patronId); // check if patron exists
        bookService.findBookById(bookId);       // check if book exists
        BorrowRecord borrowRecord = borrowRecordDao.findBorrowRecordByPatronAndBook(patronId, bookId)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("You didn't borrow a book with id=%s", bookId)
                ));
        if(borrowRecord.getReturnDate()!= null){
            throw new DuplicateRecordException(
                    String.format("You already returned this book at: %s", borrowRecord.getReturnDate()
                    ));
        }
        borrowRecord.setReturnDate(Date.valueOf(LocalDate.now()));
        borrowRecordDao.returnBook(borrowRecord);
    }
}
