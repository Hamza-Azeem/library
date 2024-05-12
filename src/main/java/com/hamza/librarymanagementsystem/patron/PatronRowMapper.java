package com.hamza.librarymanagementsystem.patron;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PatronRowMapper implements RowMapper<Patron> {
    @Override
    public Patron mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Patron(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("address")
        );
    }
}
