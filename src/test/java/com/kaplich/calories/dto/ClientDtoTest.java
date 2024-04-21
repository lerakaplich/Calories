package com.kaplich.calories.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ClientDtoTest {

    @Test
    void testClientDtoConstructorAndGetters() {
        // Arrange
        String clientName = "John Doe";
        double weight = 70.5;
        double height = 180.0;
        List<String> dishDtoList = Arrays.asList("Pizza", "Burger");

        // Act
        ClientDto clientDto = new ClientDto(clientName, weight, height, dishDtoList);

        // Assert
        Assertions.assertEquals(clientName, clientDto.getClientName());
        Assertions.assertEquals(weight, clientDto.getWeight());
        Assertions.assertEquals(height, clientDto.getHeight());
        Assertions.assertEquals(dishDtoList, clientDto.getDishDtoList());
    }

    @Test
    void testClientDtoSetters() {
        // Arrange
        ClientDto clientDto = new ClientDto();

        // Act
        String clientName = "Jane Smith";
        double weight = 60.0;
        double height = 165.0;
        List<String> dishDtoList = Arrays.asList("Salad", "Soup");

        clientDto.setClientName(clientName);
        clientDto.setWeight(weight);
        clientDto.setHeight(height);
        clientDto.setDishDtoList(dishDtoList);

        // Assert
        Assertions.assertEquals(clientName, clientDto.getClientName());
        Assertions.assertEquals(weight, clientDto.getWeight());
        Assertions.assertEquals(height, clientDto.getHeight());
        Assertions.assertEquals(dishDtoList, clientDto.getDishDtoList());
    }

    @Test
    void testClientDtoBuilder() {
        // Arrange
        String clientName = "Alex Johnson";
        double weight = 80.0;
        double height = 190.0;
        List<String> dishDtoList = Arrays.asList("Steak", "Potatoes");

        // Act
        ClientDto clientDto = ClientDto.builder()
                .clientName(clientName)
                .weight(weight)
                .height(height)
                .dishDtoList(dishDtoList)
                .build();

        // Assert
        Assertions.assertEquals(clientName, clientDto.getClientName());
        Assertions.assertEquals(weight, clientDto.getWeight());
        Assertions.assertEquals(height, clientDto.getHeight());
        Assertions.assertEquals(dishDtoList, clientDto.getDishDtoList());
    }
}
