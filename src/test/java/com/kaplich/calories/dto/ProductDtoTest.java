package com.kaplich.calories.dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductDtoTest {

    @Test
    void testProductDtoConstructorAndGetters() {
        // Arrange
        String productName = "Chicken Breast";
        double countOfCalories = 120.0;
        double protein = 25.0;
        double fat = 3.5;
        double carbohydrate = 0.0;

        // Act
        ProductDto productDto = new ProductDto(productName, countOfCalories, protein, fat, carbohydrate);

        // Assert
        Assertions.assertEquals(productName, productDto.getProductName());
        Assertions.assertEquals(countOfCalories, productDto.getCountOfCalories());
        Assertions.assertEquals(protein, productDto.getProtein());
        Assertions.assertEquals(fat, productDto.getFat());
        Assertions.assertEquals(carbohydrate, productDto.getCarbohydrate());
    }

    @Test
    void testProductDtoSetters() {
        // Arrange
        ProductDto productDto = new ProductDto();

        // Act
        String productName = "Avocado";
        double countOfCalories = 160.0;
        double protein = 2.0;
        double fat = 15.0;
        double carbohydrate = 9.0;

        productDto.setProductName(productName);
        productDto.setCountOfCalories(countOfCalories);
        productDto.setProtein(protein);
        productDto.setFat(fat);
        productDto.setCarbohydrate(carbohydrate);

        // Assert
        Assertions.assertEquals(productName, productDto.getProductName());
        Assertions.assertEquals(countOfCalories, productDto.getCountOfCalories());
        Assertions.assertEquals(protein, productDto.getProtein());
        Assertions.assertEquals(fat, productDto.getFat());
        Assertions.assertEquals(carbohydrate, productDto.getCarbohydrate());
    }

    @Test
    void testProductDtoBuilder() {
        // Arrange
        String productName = "Eggs";
        double countOfCalories = 70.0;
        double protein = 6.0;
        double fat = 5.0;
        double carbohydrate = 0.6;

        // Act
        ProductDto productDto = ProductDto.builder()
                .productName(productName)
                .countOfCalories(countOfCalories)
                .protein(protein)
                .fat(fat)
                .carbohydrate(carbohydrate)
                .build();

        // Assert
        Assertions.assertEquals(productName, productDto.getProductName());
        Assertions.assertEquals(countOfCalories, productDto.getCountOfCalories());
        Assertions.assertEquals(protein, productDto.getProtein());
        Assertions.assertEquals(fat, productDto.getFat());
        Assertions.assertEquals(carbohydrate, productDto.getCarbohydrate());
    }
}