package com.hamza.librarymanagementsystem.borrow_record;


import java.util.List;
import java.util.Optional;

public interface BorrowRecordDao {
    void borrowBook(long patronId, long bookId);

    void returnBook(BorrowRecord borrowRecord);

    public List<BorrowRecord> selectAllRecords();
    public Optional<BorrowRecord> findBorrowRecordByPatronAndBook(long patronId, long bookId);
}
