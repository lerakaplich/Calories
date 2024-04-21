package com.kaplich.calories.mapper;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.model.Dish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ClientMapperTest {

    @Test
    void testToDto() {
        // Arrange
        Client client = new Client();
        client.setClientName("John Doe");
        client.setHeight(180.0);
        client.setWeight(80.0);

        Dish dish1 = new Dish();
        dish1.setDishName("Pizza");
        Dish dish2 = new Dish();
        dish2.setDishName("Burger");
        List<Dish> dishList = Arrays.asList(dish1, dish2);
        client.setDishList(dishList);

        // Act
        ClientDto clientDto = ClientMapper.toDto(client);

        // Assert
        Assertions.assertEquals(client.getClientName(), clientDto.getClientName());
        Assertions.assertEquals(client.getHeight(), clientDto.getHeight());
        Assertions.assertEquals(client.getWeight(), clientDto.getWeight());
        Assertions.assertEquals(2, clientDto.getDishDtoList().size());
        Assertions.assertEquals("Pizza", clientDto.getDishDtoList().get(0));
        Assertions.assertEquals("Burger", clientDto.getDishDtoList().get(1));
    }

    @Test
    void testToDtoWithNullClient() {
        // Act
        ClientDto clientDto = ClientMapper.toDto(null);

        // Assert
        Assertions.assertNull(clientDto);
    }

    @Test
    void testToEntity() {
        // Arrange
        ClientDto clientDto = ClientDto.builder()
                .clientName("Jane Smith")
                .height(165.0)
                .weight(60.0)
                .dishDtoList(Arrays.asList("Salad", "Soup"))
                .build();

        // Act
        Client client = ClientMapper.toEntity(clientDto);

        // Assert
        Assertions.assertEquals(clientDto.getClientName(), client.getClientName());
        Assertions.assertEquals(clientDto.getHeight(), client.getHeight());
        Assertions.assertEquals(clientDto.getWeight(), client.getWeight());
        //Assertions.assertNull(client.getDishList()); // Dish list should be null as it is not mapped in this test
    }

    @Test
    void testToEntityWithNullClientDto() {
        // Act
        Client client = ClientMapper.toEntity(null);

        // Assert
        Assertions.assertNull(client);
    }
}