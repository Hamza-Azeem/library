package com.hamza.librarymanagementsystem.book;

import com.github.javafaker.Faker;
import com.hamza.librarymanagementsystem.TestcontainersAbstraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class BookJDBCDataAccessServiceTest extends TestcontainersAbstraction {
    private BookJDBCDataAccessService underTest;
    private final BookRowMapper bookRowMapper = new BookRowMapper();
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
        underTest = new BookJDBCDataAccessService(getJdbcTemplate(), bookRowMapper);
    }

    @Test
    void selectAllBooks() {
        // Arrange
        String isbn = faker.idNumber().valid();
        Book book = new Book(
                faker.book().title(),
                faker.book().author(),
                faker.random().nextInt(1000,2024),
                isbn
        );

        underTest.addBook(book);
        // Act
        List<Book> actual = underTest.selectAllBooks();
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(1);
    }

    @Test
    void selectBookById() {
        // Arrange
        String isbn = faker.idNumber().valid();
        String title = faker.book().title();
        String author = faker.book().author();
        int publicationYear = faker.random().nextInt(1000, 2024);
        Book book = new Book(
                title,
                author,
                publicationYear,
                isbn
        );
        underTest.addBook(book);
        long id = underTest.selectAllBooks().stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();
        // Act
        Optional<Book> actual = underTest.selectBookById(id);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getAuthor()).isEqualTo(author);
        assertThat(actual.get().getPublicationYear()).isEqualTo(publicationYear);
        assertThat(actual.get().getISBN()).isEqualTo(isbn);
    }

    @Test
    void willReturnEmptyWhenSelectBookByIdInvokedWithWrongId() {
        // Arrange
        long id = 10;
        // Act
        Optional<Book> actual = underTest.selectBookById(id);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    void addBook() {
        // Arrange
        String isbn = faker.idNumber().valid();
        String title = faker.book().title();
        String author = faker.book().author();
        int publicationYear = faker.random().nextInt(1000, 2024);
        Book book = new Book(
                title,
                author,
                publicationYear,
                isbn
        );
        // Act
        underTest.addBook(book);
        long id = underTest.selectAllBooks().stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();
        Optional<Book> actual = underTest.selectBookById(id);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getAuthor()).isEqualTo(author);
        assertThat(actual.get().getPublicationYear()).isEqualTo(publicationYear);
        assertThat(actual.get().getISBN()).isEqualTo(isbn);

    }

    @Test
    void updateBook() {
        // Arrange
        String isbn = faker.idNumber().valid();
        String title = faker.book().title();
        String author = faker.book().author();
        int publicationYear = faker.random().nextInt(1000, 2024);
        Book book = new Book(
                title,
                author,
                publicationYear,
                isbn
        );
        underTest.addBook(book);
        long id = underTest.selectAllBooks().stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();
        Book expected = new Book(
                id,
                faker.book().title(),
                faker.book().author(),
                faker.random().nextInt(1000, 2024),
                faker.idNumber().valid()
        );
        // Act
        underTest.updateBook(expected);
        Optional<Book> actual = underTest.selectBookById(id);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.get().getAuthor()).isEqualTo(expected.getAuthor());
        assertThat(actual.get().getPublicationYear()).isEqualTo(expected.getPublicationYear());
        assertThat(actual.get().getISBN()).isEqualTo(expected.getISBN());
    }

    @Test
    void deleteBookById() {
        // Arrange
        String isbn = faker.idNumber().valid();
        String title = faker.book().title();
        String author = faker.book().author();
        int publicationYear = faker.random().nextInt(1000, 2024);
        Book book = new Book(
                title,
                author,
                publicationYear,
                isbn
        );
        underTest.addBook(book);
        long id = underTest.selectAllBooks().stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();
        // Act
        underTest.deleteBookById(id);
        Optional<Book> actual = underTest.selectBookById(id);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    void bookExistsWithIsbnWillReturnTrue() {
        // Arrange
        String isbn = faker.idNumber().valid();
        String title = faker.book().title();
        String author = faker.book().author();
        int publicationYear = faker.random().nextInt(1000, 2024);
        Book book = new Book(
                title,
                author,
                publicationYear,
                isbn
        );
        underTest.addBook(book);
        // Act
        boolean actual = underTest.bookExistsWithIsbn(isbn);
        // Assert
        assertThat(actual).isTrue();
    }
    @Test
    void bookExistsWithIsbnWillReturnFalse() {
        // Arrange
        String isbn = faker.idNumber().valid();
        // Act
        boolean actual = underTest.bookExistsWithIsbn(isbn);
        // Assert
        assertThat(actual).isFalse();
    }
}