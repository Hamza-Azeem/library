package com.hamza.librarymanagementsystem.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookRowMapperTest {

    @Test
    void canMap() throws SQLException {
        // Arrange
        BookRowMapper bookRowMapper = new BookRowMapper();
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        // Act
        when(resultSet.getLong("id")).thenReturn(1l);
        when(resultSet.getString("title")).thenReturn("title");
        when(resultSet.getString("author")).thenReturn("author");
        when(resultSet.getInt("publication_year")).thenReturn(2023);
        when(resultSet.getString("isbn")).thenReturn("326321215");
        Book book = bookRowMapper.mapRow(resultSet, 1);
        // Assert
        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getTitle()).isEqualTo("title");
        assertThat(book.getAuthor()).isEqualTo("author");
        assertThat(book.getPublicationYear()).isEqualTo(2023);
        assertThat(book.getISBN()).isEqualTo("326321215");
    }
}