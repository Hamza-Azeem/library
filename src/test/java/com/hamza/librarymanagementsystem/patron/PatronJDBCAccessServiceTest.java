package com.hamza.librarymanagementsystem.patron;

import com.github.javafaker.Faker;
import com.hamza.librarymanagementsystem.TestcontainersAbstraction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PatronJDBCAccessServiceTest extends TestcontainersAbstraction {
    private final PatronRowMapper patronRowMapper = new PatronRowMapper();
    private PatronJDBCAccessService underTest;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        underTest = new PatronJDBCAccessService(getJdbcTemplate(), patronRowMapper);
    }

    @Test
    void selectAllPatrons() {
        // Arrange
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                name,
                email,
                phoneNumber,
                address
        );
        underTest.addPatron(patron);
        // Act
        List<Patron> actual = underTest.selectAllPatrons();
        // Assert
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectPatronById() {
        // Arrange
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                name,
                email,
                phoneNumber,
                address
        );
        underTest.addPatron(patron);
        long id = underTest.selectAllPatrons()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        // Act
        Optional<Patron> actual = underTest.selectPatronById(id);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getName()).isEqualTo(name);
        assertThat(actual.get().getEmail()).isEqualTo(email);
        assertThat(actual.get().getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.get().getAddress()).isEqualTo(address);
    }

    @Test
    void selectPatronByIdWillReturnEmptyWhenInvokedWithWrongId() {
        // Arrange
        long id = 0;
        // Act
        Optional<Patron> actual = underTest.selectPatronById(id);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    void addPatron() {
        // Arrange
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                name,
                email,
                phoneNumber,
                address
        );
        // Act
        underTest.addPatron(patron);
        // Assert
        long id = underTest.selectAllPatrons()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        Optional<Patron> actual = underTest.selectPatronById(id);
        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getName()).isEqualTo(name);
        assertThat(actual.get().getEmail()).isEqualTo(email);
        assertThat(actual.get().getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.get().getAddress()).isEqualTo(address);
    }

    @Test
    void updatePatron() {
        // Arrange
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                name,
                email,
                phoneNumber,
                address
        );
        underTest.addPatron(patron);
        long id = underTest.selectAllPatrons()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        Patron expected = new Patron(
                id,
                name+"updated",
                "updated"+email,
                phoneNumber+"0",
                address+"updated"
        );
        // Act
        underTest.updatePatron(expected);
        Patron actual = underTest.selectAllPatrons()
                .stream()
                .filter(p -> p.getEmail().equals("updated"+email))
                .findFirst().orElseThrow();
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
        assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
    }

    @Test
    void deletePatronById() {
        // Arrange
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                name,
                email,
                phoneNumber,
                address
        );
        underTest.addPatron(patron);
        long id = underTest.selectAllPatrons()
                .stream()
                .filter(p -> p.getEmail().equals(email))
                .map(Patron::getId)
                .findFirst().orElseThrow();
        // Act
        underTest.deletePatronById(id);
        Optional<Patron> actual = underTest.selectPatronById(id);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    void patronExistsWithEmail() {
        // Arrange
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                name,
                email,
                phoneNumber,
                address
        );
        underTest.addPatron(patron);
        // Act
        boolean actual = underTest.patronExistsWithEmail(email);
        // Assert
        assertThat(actual).isTrue();
    }

    @Test
    void patronExistsWithEmailWillReturnFalseWhenInvokedWithNewEmail() {
        // Arrange
        String email = faker.internet().emailAddress();
        // Act
        boolean actual = underTest.patronExistsWithEmail(email);
        // Assert
        assertThat(actual).isFalse();
    }
}