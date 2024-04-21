package com.kaplich.calories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CaloriesApplicationTests {

    @Test
    void main() {
        SpringApplication applicationMock = mock(SpringApplication.class);
        String[] args = new String[0];

        // Вызов метода main()
        CaloriesApplication.main(args);

    }
}