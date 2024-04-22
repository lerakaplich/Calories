package com.kaplich.calories.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DishDtoTest {

    @Test
    void testDishDtoConstructorAndGetters() {
        // Arrange
        String dishName = "Pizza";
        double countOfCalories = 1200.0;
        List<ProductDto> productList = Arrays.asList(
                new ProductDto("Flour", 200.0, 77, 99, 46),
                new ProductDto("Cheese", 400.0, 35, 67.5, 34.8)
        );
        ClientDto clientDto = new ClientDto("John Doe", 70.5, 180.0, Arrays.asList("Pizza", "Burger"));

        // Act
        DishDto dishDto = new DishDto(dishName, countOfCalories, productList, clientDto);

        // Assert
        Assertions.assertEquals(dishName, dishDto.getDishName());
        Assertions.assertEquals(countOfCalories, dishDto.getCountOfCalories());
        Assertions.assertEquals(productList, dishDto.getProductList());
        Assertions.assertEquals(clientDto, dishDto.getClientDto());
    }

    @Test
    void testDishDtoSetters() {
        // Arrange
        DishDto dishDto = new DishDto();

        // Act
        String dishName = "Burger";
        double countOfCalories = 900.0;
        List<ProductDto> productList = Arrays.asList(
                new ProductDto("Bun", 200.0, 78, 8, 87),
                new ProductDto("Beef Patty", 300.0, 675, 34, 98)
        );
        ClientDto clientDto = new ClientDto("Jane Smith", 60.0, 165.0, Arrays.asList("Salad", "Soup"));

        dishDto.setDishName(dishName);
        dishDto.setCountOfCalories(countOfCalories);
        dishDto.setProductList(productList);
        dishDto.setClientDto(clientDto);

        // Assert
        Assertions.assertEquals(dishName, dishDto.getDishName());
        Assertions.assertEquals(countOfCalories, dishDto.getCountOfCalories());
        Assertions.assertEquals(productList, dishDto.getProductList());
        Assertions.assertEquals(clientDto, dishDto.getClientDto());
    }

    @Test
    void testDishDtoBuilder() {
        // Arrange
        String dishName = "Steak";
        double countOfCalories = 1500.0;
        List<ProductDto> productList = Arrays.asList(
                new ProductDto("Beef", 1000.0, 67, 34, 76),
                new ProductDto("Seasoning", 200.0, 35, 78, 35)
        );
        ClientDto clientDto = new ClientDto("Alex Johnson", 80.0, 190.0, Arrays.asList("Steak", "Potatoes"));

        // Act
        DishDto dishDto = DishDto.builder()
                .dishName(dishName)
                .countOfCalories(countOfCalories)
                .productList(productList)
                .clientDto(clientDto)
                .build();

        // Assert
        Assertions.assertEquals(dishName, dishDto.getDishName());
        Assertions.assertEquals(countOfCalories, dishDto.getCountOfCalories());
        Assertions.assertEquals(productList, dishDto.getProductList());
        Assertions.assertEquals(clientDto, dishDto.getClientDto());
    }
}
