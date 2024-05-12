package com.hamza.librarymanagementsystem.patron;

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
class PatronServiceTest {
    private PatronService underTest;
    private final Faker faker = new Faker();
    @Mock
    private PatronDao patronDao;
    @BeforeEach
    void setUp() {
        underTest = new PatronService(patronDao);
    }

    @Test
    void findAllPatrons() {
        // Act
        underTest.findAllPatrons();
        // Assert
        verify(patronDao).selectAllPatrons();
    }

    @Test
    void findPatronById() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        Patron actual = underTest.findPatronById(id);
        // Assert
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }
    @Test
    void findPatronByIdWillThrowErrorWhenInvokedWithWrongId() {
        // Arrange
        long id = 1L;
        when(patronDao.selectPatronById(id)).thenReturn(Optional.empty());
        // Assert
        assertThatThrownBy(()->underTest.findPatronById(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("No patron was found with id=%s", id));
    }

    @Test
    void addPatron() {
        // Arrange
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        PatronRegistrationRequest patronRegistrationRequest = new PatronRegistrationRequest(
                name,
                email,
                phoneNumber,
                address
        );
        when(patronDao.patronExistsWithEmail(email)).thenReturn(false);
        // Act
        underTest.addPatron(patronRegistrationRequest);
        ArgumentCaptor<Patron> argumentCaptor = ArgumentCaptor.forClass(Patron.class);
        // Assert
        verify(patronDao).addPatron(argumentCaptor.capture());
        Patron actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(0);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    void addPatronWillThrowExceptionWhenInvokedWithDuplicateEmail() {
        // Arrange
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        PatronRegistrationRequest patronRegistrationRequest = new PatronRegistrationRequest(
                name,
                email,
                phoneNumber,
                address
        );
        when(patronDao.patronExistsWithEmail(email)).thenReturn(true);
        // Assert
        assertThatThrownBy(()->underTest.addPatron(patronRegistrationRequest))
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("Email is already taken.");
    }

    @Test
    void updatePatronAllAttributes() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        String nameUpdated = faker.funnyName().name();
        String emailUpdated = faker.internet().emailAddress();
        String phoneNumberUpdated = faker.phoneNumber().phoneNumber();
        String addressUpdated = faker.address().fullAddress();
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                nameUpdated,
                emailUpdated,
                phoneNumberUpdated,
                addressUpdated
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        underTest.updatePatron(id, patronUpdateRequest);
        ArgumentCaptor<Patron> argumentCaptor = ArgumentCaptor.forClass(Patron.class);
        // Assert
        verify(patronDao).updatePatron(argumentCaptor.capture());
        Patron actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(nameUpdated);
        assertThat(actual.getEmail()).isEqualTo(emailUpdated);
        assertThat(actual.getAddress()).isEqualTo(addressUpdated);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumberUpdated);
    }


    @Test
    void updatePatronName() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        String nameUpdated = faker.funnyName().name();
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                nameUpdated,
                null,
                null,
                null
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        underTest.updatePatron(id, patronUpdateRequest);
        ArgumentCaptor<Patron> argumentCaptor = ArgumentCaptor.forClass(Patron.class);
        // Assert
        verify(patronDao).updatePatron(argumentCaptor.capture());
        Patron actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(nameUpdated);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getAddress()).isEqualTo(address);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
    }
    @Test
    void updatePatronEmail() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        String emailUpdated = faker.internet().emailAddress();
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                null,
                emailUpdated,
                null,
                null
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        when(patronDao.patronExistsWithEmail(emailUpdated)).thenReturn(false);
        // Act
        underTest.updatePatron(id, patronUpdateRequest);
        ArgumentCaptor<Patron> argumentCaptor = ArgumentCaptor.forClass(Patron.class);
        // Assert
        verify(patronDao).updatePatron(argumentCaptor.capture());
        Patron actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(emailUpdated);
        assertThat(actual.getAddress()).isEqualTo(address);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
    }
    @Test
    void updatePatronPhoneNumber() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        String phoneNumberUpdated = faker.phoneNumber().phoneNumber();
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                null,
                null,
                phoneNumberUpdated,
                null
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        underTest.updatePatron(id, patronUpdateRequest);
        ArgumentCaptor<Patron> argumentCaptor = ArgumentCaptor.forClass(Patron.class);
        // Assert
        verify(patronDao).updatePatron(argumentCaptor.capture());
        Patron actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getAddress()).isEqualTo(address);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumberUpdated);
    }
    @Test
    void updatePatronAddress() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        String addressUpdated = faker.address().fullAddress();
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                null,
                null,
                null,
                addressUpdated
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        underTest.updatePatron(id, patronUpdateRequest);
        ArgumentCaptor<Patron> argumentCaptor = ArgumentCaptor.forClass(Patron.class);
        // Assert
        verify(patronDao).updatePatron(argumentCaptor.capture());
        Patron actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getAddress()).isEqualTo(addressUpdated);
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
    }
    @Test
    void updatePatronWillThrowExceptionWhenAllAttributesAreNull() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                null,
                null,
                null,
                null
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        assertThatThrownBy(() -> underTest.updatePatron(id, patronUpdateRequest))
                .isInstanceOf(RecordNotModifiedException.class)
                .hasMessage("No changes found!");
    }
    @Test
    void updatePatronWillThrowExceptionWhenAllAttributesAreNotChanged() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                name,
                email,
                phoneNumber,
                address
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        assertThatThrownBy(() -> underTest.updatePatron(id, patronUpdateRequest))
                .isInstanceOf(RecordNotModifiedException.class)
                .hasMessage("No changes found!");
    }
    @Test
    void updatePatronWillThrowExceptionWhenEmailIsDuplicated() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        PatronUpdateRequest patronUpdateRequest = new PatronUpdateRequest(
                name+"updated",
                "updated"+email,
                null,
                null
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        when(patronDao.patronExistsWithEmail("updated"+email)).thenReturn(true);
        // Act
        assertThatThrownBy(() -> underTest.updatePatron(id, patronUpdateRequest))
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("Email is already taken.");
    }
    @Test
    void deletePatronById() {
        // Arrange
        long id = 1L;
        String name = faker.funnyName().name();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        Patron patron = new Patron(
                id,
                name,
                email,
                phoneNumber,
                address
        );
        when(patronDao.selectPatronById(id)).thenReturn(Optional.of(patron));
        // Act
        underTest.deletePatronById(id);
        // Assert
        verify(patronDao).deletePatronById(id);
    }
    @Test
    void deletePatronByIdWillThrowExceptionWhenInvokedWithWrongId() {
        // Arrange
        long id = 1L;
        when(patronDao.selectPatronById(id)).thenReturn(Optional.empty());
        // Assert
        assertThatThrownBy(()-> underTest.deletePatronById(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("No patron was found with id=%s", id));
    }
}