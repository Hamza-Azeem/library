package com.hamza.librarymanagementsystem.book;

import com.github.javafaker.Faker;
import com.hamza.librarymanagementsystem.exception.DuplicateRecordException;
import com.hamza.librarymanagementsystem.exception.RecordNotFoundException;
import com.hamza.librarymanagementsystem.exception.RecordNotModifiedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {
    private BookService underTest;
    @Mock
    private BookDao bookDao;
    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookDao);
    }

    @Test
    void findAllBooks() {
        underTest.findAllBooks();
        // Assert
        verify(bookDao).selectAllBooks();
    }

    @Test
    void findBookById() {
        // Arrange
        long id = 1;
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Act
        Book actual = underTest.findBookById(id);
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getAuthor()).isEqualTo(author);
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getPublicationYear()).isEqualTo(publicationYear);
        assertThat(actual.getISBN()).isEqualTo(isbn);
    }

    @Test
    void findBookByIdWillThrowExceptionWhenInvokedWithWrongId() {
        // Arrange
        long id = 1;
        when(bookDao.selectBookById(id)).thenReturn(Optional.empty());
        // Assert
        assertThatThrownBy(()->underTest.findBookById(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("No book with id=%s was found.", id));

    }

    @Test
    void addNewBook() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest(
                title,
                author,
                publicationYear,
                isbn
        );
        when(bookDao.bookExistsWithIsbn(isbn)).thenReturn(false);
        // Act
        underTest.addNewBook(bookRegistrationRequest);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        // Assert
        verify(bookDao).addBook(argumentCaptor.capture());
        Book actual = argumentCaptor.getValue();
        assertThat(actual.getISBN()).isEqualTo(isbn);
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getAuthor()).isEqualTo(author);
        assertThat(actual.getPublicationYear()).isEqualTo(publicationYear);
        assertThat(actual.getId()).isZero();
    }

    @Test
    void addNewBookWillThrowExceptionWhenInvokedWithDuplicateISBN() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest(
                title,
                author,
                publicationYear,
                isbn
        );
        when(bookDao.bookExistsWithIsbn(isbn)).thenReturn(true);
        // Assert
        assertThatThrownBy(()-> underTest.addNewBook(bookRegistrationRequest))
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("This ISBN is already taken.");
    }

    @Test
    void updateExistingBookTitle() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        String titleUpdated = title + "updated";
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                titleUpdated,
                null,
                null,
                null
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Act
        underTest.updateExistingBook(id, bookUpdateRequest);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        // Assert
        verify(bookDao).updateBook(argumentCaptor.capture());
        Book actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getISBN()).isEqualTo(isbn);
        assertThat(actual.getTitle()).isEqualTo(titleUpdated);
        assertThat(actual.getAuthor()).isEqualTo(author);
        assertThat(actual.getPublicationYear()).isEqualTo(publicationYear);
    }

    @Test
    void updateExistingBookAuthor() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        String authorUpdated = author + "updated";
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                null,
                authorUpdated,
                null,
                null
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Act
        underTest.updateExistingBook(id, bookUpdateRequest);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        // Assert
        verify(bookDao).updateBook(argumentCaptor.capture());
        Book actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getISBN()).isEqualTo(isbn);
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getAuthor()).isEqualTo(authorUpdated);
        assertThat(actual.getPublicationYear()).isEqualTo(publicationYear);
    }
 @Test
    void updateExistingBookPublicationYear() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        Integer publicationYearUpdated = publicationYear+1;
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                null,
                null,
                publicationYearUpdated,
                null
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Act
        underTest.updateExistingBook(id, bookUpdateRequest);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        // Assert
        verify(bookDao).updateBook(argumentCaptor.capture());
        Book actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getISBN()).isEqualTo(isbn);
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getAuthor()).isEqualTo(author);
        assertThat(actual.getPublicationYear()).isEqualTo(publicationYearUpdated);
    }
    @Test
    void updateExistingBookISBN() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        String isbnUpdated = isbn+"2";
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                null,
                null,
                null,
                isbnUpdated
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        when(bookDao.bookExistsWithIsbn(isbnUpdated)).thenReturn(false);
        // Act
        underTest.updateExistingBook(id, bookUpdateRequest);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        // Assert
        verify(bookDao).updateBook(argumentCaptor.capture());
        Book actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getISBN()).isEqualTo(isbnUpdated);
        assertThat(actual.getTitle()).isEqualTo(title);
        assertThat(actual.getAuthor()).isEqualTo(author);
        assertThat(actual.getPublicationYear()).isEqualTo(publicationYear);
    }
    @Test
    void updateExistingBookAllAttributes() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                title+"updated",
                author+"updated",
                publicationYear+1,
                isbn+"0"
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        when(bookDao.bookExistsWithIsbn(bookUpdateRequest.ISBN())).thenReturn(false);
        // Act
        underTest.updateExistingBook(id, bookUpdateRequest);
        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        // Assert
        verify(bookDao).updateBook(argumentCaptor.capture());
        Book actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getISBN()).isEqualTo(bookUpdateRequest.ISBN());
        assertThat(actual.getTitle()).isEqualTo(bookUpdateRequest.title());
        assertThat(actual.getAuthor()).isEqualTo(bookUpdateRequest.author());
        assertThat(actual.getPublicationYear()).isEqualTo(bookUpdateRequest.publicationYear());
    }

    @Test
    void updateExistingBookISBNWillThrowExceptionWhenInvokedWithDuplicateISBN() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                null,
                null,
                null,
                isbn+"0"
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        when(bookDao.bookExistsWithIsbn(bookUpdateRequest.ISBN()))
                .thenReturn(true);
        // Assert
        assertThatThrownBy(
                () ->
                underTest.updateExistingBook(id, bookUpdateRequest))
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("This ISBN is already taken.");
    }
    @Test
    void updateExistingBookISBNWillThrowExceptionWhenInvokedWithWrongId() {
        // Arrange
        long id = 1;
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                null,
                null,
                null,
                null
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.empty());
        // Assert
        assertThatThrownBy(
                () ->
                underTest.updateExistingBook(id, bookUpdateRequest))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("No book with id=%s was found.", id));
    }
    @Test
    void updateExistingBookISBNWillThrowExceptionWhenInvokedWithNullValues() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                null,
                null,
                null,
                null
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Assert
        assertThatThrownBy(
                () ->
                underTest.updateExistingBook(id, bookUpdateRequest))
                .isInstanceOf(RecordNotModifiedException.class)
                .hasMessage("No updates found!");
    }
    @Test
    void updateExistingBookISBNWillThrowExceptionWhenInvokedWithNoUpdates() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                title,
                author,
                publicationYear,
                isbn
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Assert
        assertThatThrownBy(
                () ->
                underTest.updateExistingBook(id, bookUpdateRequest))
                .isInstanceOf(RecordNotModifiedException.class)
                .hasMessage("No updates found!");
    }
    @Test
    void deleteBookById() {
        // Arrange
        String title = faker.book().title();
        String author = faker.book().author();
        Integer publicationYear = faker.random().nextInt(1000, 2024);
        String isbn = faker.idNumber().valid();
        long id = 1;
        Book book = new Book(
                id,
                title,
                author,
                publicationYear,
                isbn
        );
        when(bookDao.selectBookById(id)).thenReturn(Optional.of(book));
        // Act
        underTest.deleteBookById(id);
        // Assert
        verify(bookDao).deleteBookById(id);
    }
    @Test
    void deleteBookByIdWillThrowExceptionWhenInvokedWithWrongId() {
        // Arrange
        long id = 1;
        when(bookDao.selectBookById(id)).thenReturn(Optional.empty());

        // Assert
        assertThatThrownBy(() -> underTest.deleteBookById(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("No book with id=%s was found.", id));
    }
}