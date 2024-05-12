package com.hamza.librarymanagementsystem.patron;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PatronRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Arrange
        PatronRowMapper patronRowMapper = new PatronRowMapper();
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Patron expected = new Patron(
                1L,
                "hamza",
                "hamza@gmail.com",
                "01241512",
                "giza"
        );
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("name")).thenReturn(expected.getName());
        when(resultSet.getString("email")).thenReturn(expected.getEmail());
        when(resultSet.getString("phone_number")).thenReturn(expected.getPhoneNumber());
        when(resultSet.getString("address")).thenReturn(expected.getAddress());
        // Act
        Patron actual = patronRowMapper.mapRow(resultSet, 1);
        // Assert
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
        assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
    }
}