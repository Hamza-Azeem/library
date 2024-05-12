package com.hamza.librarymanagementsystem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestcontainersTest extends TestcontainersAbstraction {

    @Test
    void canTestContainerRun() {
        // Assert
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}
