package com.hamza.librarymanagementsystem.book;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class BookJDBCDataAccessService implements BookDao {
    private final JdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    public BookJDBCDataAccessService(JdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = bookRowMapper;
    }

    @Override
    public List<Book> selectAllBooks() {
        String sql = """
                SELECT * FROM book;
                """;
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public Optional<Book> selectBookById(long id) {
        String sql = """
                SELECT * FROM book WHERE id=?
                """;

        return jdbcTemplate.query(sql, bookRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void addBook(Book book) {
        String sql = """
                INSERT INTO book(title, author, publication_year, isbn)
                VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                sql,
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getISBN()
        );
    }

    @Override
    public void updateBook(Book book) {
        String sql = """
                UPDATE book SET title=?, author=?, publication_year=?, ISBN=?
                WHERE id=?
                """;
        jdbcTemplate.update(
                sql,
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getISBN(),
                book.getId()
        );
    }

    @Override
    public void deleteBookById(long id) {
        String sql = """
                DELETE FROM book WHERE id=?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean bookExistsWithIsbn(String isbn) {
        String sql = """
                SELECT COUNT(id) FROM book WHERE isbn=? LIMIT 1
                """;
        int count = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        return count == 1 ? true : false;
    }
}
