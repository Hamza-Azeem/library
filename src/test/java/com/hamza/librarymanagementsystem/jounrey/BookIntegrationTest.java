package com.hamza.librarymanagementsystem.jounrey;

import com.github.javafaker.Faker;
import com.hamza.librarymanagementsystem.book.Book;
import com.hamza.librarymanagementsystem.book.BookRegistrationRequest;
import com.hamza.librarymanagementsystem.book.BookUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookIntegrationTest {
    @Autowired
    private  WebTestClient webTestClient;
    private final String BOOK_URI = "/api/books";
    private final Faker faker = new Faker();

    @Test
    void canAddBook() {
        // Arrange
        String isbn = faker.idNumber().valid();
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest(
                "title",
                "author",
                2024,
                isbn
        );
        // Act
        webTestClient.post()
                .uri(BOOK_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookRegistrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();

        List<Book> books = webTestClient.get()
                .uri(BOOK_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Book>() {})
                .returnResult()
                .getResponseBody();
        long id = books.stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();
        Book actual = webTestClient.get()
                .uri(BOOK_URI+ "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Book>() {})
                .returnResult()
                .getResponseBody();
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getAuthor()).isEqualTo("author");
        assertThat(actual.getPublicationYear()).isEqualTo(2024);
        assertThat(actual.getISBN()).isEqualTo(isbn);
        assertThat(actual.getTitle()).isEqualTo("title");
    }
    @Test
    void canUpdateBook() {
        // Arrange
        String isbn = faker.idNumber().valid();
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest(
                "title",
                "author",
                2024,
                isbn
        );
        String updatedTitle = faker.book().title();
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                updatedTitle,
                null,
                null,
                null
        );
        // Act
        webTestClient.post()
                .uri(BOOK_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookRegistrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();

        List<Book> books = webTestClient.get()
                .uri(BOOK_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Book>() {})
                .returnResult()
                .getResponseBody();
        long id = books.stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();

        webTestClient.put()
                .uri(BOOK_URI + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookUpdateRequest)
                .exchange()
                .expectStatus()
                .isOk();

        Book actual = webTestClient.get()
                .uri(BOOK_URI+ "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Book>() {})
                .returnResult()
                .getResponseBody();
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getAuthor()).isEqualTo("author");
        assertThat(actual.getPublicationYear()).isEqualTo(2024);
        assertThat(actual.getISBN()).isEqualTo(isbn);
        assertThat(actual.getTitle()).isEqualTo(updatedTitle);
    }
    @Test
    void canDeleteBook() {
        // Arrange
        String isbn = faker.idNumber().valid();
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest(
                "title",
                "author",
                2024,
                isbn
        );
        String updatedTitle = faker.book().title();
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                updatedTitle,
                null,
                null,
                null
        );
        // Act
        webTestClient.post()
                .uri(BOOK_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookRegistrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();
        List<Book> books = webTestClient.get()
                .uri(BOOK_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Book>() {})
                .returnResult()
                .getResponseBody();

        long id = books.stream()
                .filter(b -> b.getISBN().equals(isbn))
                .map(Book::getId)
                .findFirst().orElseThrow();

        webTestClient.delete()
                .uri(BOOK_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNoContent();

        webTestClient.get()
                .uri(BOOK_URI+ "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

}
