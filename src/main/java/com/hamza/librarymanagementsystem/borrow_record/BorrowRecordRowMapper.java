package com.hamza.librarymanagementsystem.borrow_record;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BorrowRecordRowMapper implements RowMapper<BorrowRecord> {
    @Override
    public BorrowRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BorrowRecord(
                rs.getLong("patron_id"),
                rs.getLong("book_id"),
                rs.getDate("borrow_date"),
                rs.getDate("return_date")
        );
    }
}
