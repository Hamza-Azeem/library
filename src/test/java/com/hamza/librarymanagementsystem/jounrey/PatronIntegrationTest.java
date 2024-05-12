package com.hamza.librarymanagementsystem.jounrey;

import com.github.javafaker.Faker;
import com.hamza.librarymanagementsystem.patron.Patron;
import com.hamza.librarymanagementsystem.patron.PatronRegistrationRequest;
import com.hamza.librarymanagementsystem.patron.PatronUpdateRequest;
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
public class PatronIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    private final Faker faker = new Faker();
    private final String PATRON_URI = "/api/patrons";
    @Test
    void canAddPatron() {
        // Arrange
        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        PatronRegistrationRequest patronRegistrationRequest =
                new PatronRegistrationRequest(
                        name,
                        email,
                        phoneNumber,
                        address
                );
        // Act
        webTestClient.post()
                .uri(PATRON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patronRegistrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();
        List<Patron> patrons = webTestClient.get()
                .uri(PATRON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Patron>() {
                }).returnResult().getResponseBody();
        long id = patrons.stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        Patron actual = webTestClient.get()
                .uri(PATRON_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Patron>() {
                }).returnResult().getResponseBody();
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }
    @Test
    void canUpdatePatron() {
        // Arrange
        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        PatronRegistrationRequest patronRegistrationRequest =
                new PatronRegistrationRequest(
                        name,
                        email,
                        phoneNumber,
                        address
                );
        PatronUpdateRequest patronUpdateRequest =
                new PatronUpdateRequest(
                        name+"updated",
                        email,
                        phoneNumber,
                        address
                );
        webTestClient.post()
                .uri(PATRON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patronRegistrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();
        List<Patron> patrons = webTestClient.get()
                .uri(PATRON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Patron>() {
                }).returnResult().getResponseBody();
        long id = patrons.stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        // Act
        webTestClient.put()
                .uri(PATRON_URI+ "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patronUpdateRequest)
                .exchange()
                .expectStatus()
                .isOk();
        Patron actual = webTestClient.get()
                .uri(PATRON_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Patron>() {
                }).returnResult().getResponseBody();
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name+"updated");
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }
    @Test
    void canDeletePatron() {
        // Arrange
        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        PatronRegistrationRequest patronRegistrationRequest =
                new PatronRegistrationRequest(
                        name,
                        email,
                        phoneNumber,
                        address
                );
        webTestClient.post()
                .uri(PATRON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patronRegistrationRequest)
                .exchange()
                .expectStatus()
                .isCreated();
        List<Patron> patrons = webTestClient.get()
                .uri(PATRON_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Patron>() {
                }).returnResult().getResponseBody();
        long id = patrons.stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        // Act
        webTestClient.delete()
                .uri(PATRON_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNoContent();
        // Assert
        webTestClient.get()
                .uri(PATRON_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
