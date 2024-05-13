package com.hamza.librarymanagementsystem.borrow_record;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class BorrowRecordJDBCAccessService implements BorrowRecordDao {
    private final JdbcTemplate jdbcTemplate;
    private final BorrowRecordRowMapper borrowRecordRowMapper;
    public BorrowRecordJDBCAccessService(JdbcTemplate jdbcTemplate, BorrowRecordRowMapper borrowRecordRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.borrowRecordRowMapper = borrowRecordRowMapper;
    }

    @Transactional
    @Modifying
    @Override
    public void borrowBook(long patronId, long bookId) {
        String sql = """
                INSERT INTO borrow_record(patron_id, book_id, borrow_date)
                VALUES(?, ?, ?)
                """;
        jdbcTemplate.update(
                sql,
                patronId,
                bookId,
                Date.valueOf(LocalDate.now())
        );
    }
    @Override
    @Transactional(readOnly = false)
    @Modifying
    public void returnBook(BorrowRecord borrowRecord) {
        String sql= """
                UPDATE borrow_record SET return_date=?
                WHERE patron_id=? AND book_id=?
                """;
        int n = jdbcTemplate.update(
                sql,
                borrowRecord.getReturnDate(),
                borrowRecord.getPatronId(),
                borrowRecord.getBookId()
        );
    }
    // Just for testing:
    @Override
    public List<BorrowRecord> selectAllRecords(){
        String sql = """
                SELECT * FROM borrow_record
                """;
        return jdbcTemplate.query(
                sql,
                borrowRecordRowMapper
        );
    }
    @Override
    public Optional<BorrowRecord> findBorrowRecordByPatronAndBook(long patronId, long bookId) {
        String sql = """
                SELECT * FROM borrow_record
                WHERE patron_id=? AND book_id=?
                """;
        Optional<BorrowRecord> borrowRecord = jdbcTemplate.query(
                sql,
                borrowRecordRowMapper,
                patronId,
                bookId
        ).stream().findFirst();
        return borrowRecord;
    }
}
