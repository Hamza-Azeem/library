package com.hamza.librarymanagementsystem.patron;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatronJDBCAccessService implements PatronDao{
    private final JdbcTemplate jdbcTemplate;
    private final PatronRowMapper patronRowMapper;

    public PatronJDBCAccessService(JdbcTemplate jdbcTemplate, PatronRowMapper patronRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.patronRowMapper = patronRowMapper;
    }

    @Override
    public List<Patron> selectAllPatrons() {
        String sql = """
                SELECT * FROM patron
                """;
        return jdbcTemplate.query(sql, patronRowMapper);
    }

    @Override
    public Optional<Patron> selectPatronById(long id) {
        String sql = """
                SELECT * FROM patron WHERE id=?
                """;
        Optional<Patron> patron = jdbcTemplate.query(sql, patronRowMapper, id).stream().findFirst();
        return patron;
    }

    @Override
    public void addPatron(Patron patron) {
        String sql = """
                INSERT INTO patron(name, email, phone_number, address)
                VALUES(?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                sql,
                patron.getName(),
                patron.getEmail(),
                patron.getPhoneNumber(),
                patron.getAddress()
                );
    }

    @Override
    public void updatePatron(Patron patron) {
        String sql = """
                UPDATE patron
                SET name=?, email=?, phone_number=?, address=?
                WHERE id=?
                """;
        jdbcTemplate.update(
                sql,
                patron.getName(),
                patron.getEmail(),
                patron.getPhoneNumber(),
                patron.getAddress(),
                patron.getId()
        );
    }

    @Override
    public void deletePatronById(long id) {
        String sql = """
                DELETE FROM patron WHERE id=?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean patronExistsWithEmail(String email) {
        String sql = """
                SELECT COUNT(id) FROM patron WHERE email=?
                """;
        int c = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return c > 0 ? true : false;
    }
}
