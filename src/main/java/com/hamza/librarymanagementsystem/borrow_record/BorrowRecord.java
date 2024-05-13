package com.hamza.librarymanagementsystem.borrow_record;

import java.sql.Date;

public class BorrowRecord {
    private long patronId;
    private long bookId;
    private Date borrowDate;
    private Date returnDate;

    public BorrowRecord() {
    }
    public BorrowRecord(long patronId, long bookId, Date borrowDate, Date returnDate) {
        this.patronId = patronId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public long getPatronId() {
        return patronId;
    }

    public void setPatronId(long patronId) {
        this.patronId = patronId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BookRecord{" +
                "patronId=" + patronId +
                ", bookId=" + bookId +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
