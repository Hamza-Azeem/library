package com.hamza.librarymanagementsystem.borrow_record;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowRecordController {
    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    private ResponseEntity<?> borrowBook(@PathVariable("bookId") long bookId,
                                         @PathVariable("patronId") long patronId){
        borrowRecordService.borrowBook(patronId, bookId);
        return new ResponseEntity<>(Void.class, HttpStatus.CREATED);
    }
    @PutMapping("/return/{bookId}/patron/{patronId}")
    private ResponseEntity<?> returnBook(@PathVariable("bookId") long bookId,
                                         @PathVariable("patronId") long patronId){
        borrowRecordService.returnBook(patronId, bookId);
        return new ResponseEntity<>(Void.class, HttpStatus.OK);
    }
}
