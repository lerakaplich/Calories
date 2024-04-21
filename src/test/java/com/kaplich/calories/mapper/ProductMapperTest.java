package com.kaplich.calories.mapper;

import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.mapper.ProductMapper;
import com.kaplich.calories.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductMapperTest {

    @Test
    void testToDto() {
        // Arrange
        Product product = new Product();
        product.setProductName("Apple");
        product.setCountOfCalories(50.0);
        product.setProtein(0.5);
        product.setFat(0.3);

        // Act
        ProductDto productDto = ProductMapper.toDto(product);

        // Assert
        Assertions.assertEquals(product.getProductName(), productDto.getProductName());
        Assertions.assertEquals(product.getCountOfCalories(), productDto.getCountOfCalories());
        Assertions.assertEquals(product.getProtein(), productDto.getProtein());
        Assertions.assertEquals(product.getFat(), productDto.getFat());
    }

    @Test
    void testToDtoWithNullProduct() {
        // Act
        ProductDto productDto = ProductMapper.toDto(null);

        // Assert
        Assertions.assertNull(productDto);
    }

    @Test
    void testToEntity() {
        // Arrange
        ProductDto productDto = ProductDto.builder()
                .productName("Banana")
                .countOfCalories(100.0)
                .protein(1.0)
                .fat(0.2)
                .build();

        // Act
        Product product = ProductMapper.toEntity(productDto);

        // Assert
        Assertions.assertEquals(productDto.getProductName(), product.getProductName());
        Assertions.assertEquals(productDto.getCountOfCalories(), product.getCountOfCalories());
        Assertions.assertEquals(productDto.getProtein(), product.getProtein());
        Assertions.assertEquals(productDto.getFat(), product.getFat());
        Assertions.assertEquals(productDto.getCarbohydrate(), product.getCarbohydrate()); // Not mapped in this test
    }

    @Test
    void testToEntityWithNullProductDto() {
        // Act
        Product product = ProductMapper.toEntity(null);

        // Assert
        Assertions.assertNull(product);
    }

    @Test
    void testToDtoWithPartialFields() {
        // Arrange
        Product product = new Product();
        product.setProductName("Milk");
        product.setCountOfCalories(150.0);

        // Act
        ProductDto productDto = ProductMapper.toDto(product);

        // Assert
        Assertions.assertEquals(product.getProductName(), productDto.getProductName());
        Assertions.assertEquals(product.getCountOfCalories(), productDto.getCountOfCalories());
        Assertions.assertNull(productDto.getProtein());
        Assertions.assertNull(productDto.getFat());
    }

    @Test
    void testToEntityWithPartialFields() {
        // Arrange
        ProductDto productDto = ProductDto.builder()
                .productName("Yogurt")
                .countOfCalories(120.0)
                .build();

        // Act
        Product product = ProductMapper.toEntity(productDto);

        // Assert
        Assertions.assertEquals(productDto.getProductName(), product.getProductName());
        Assertions.assertEquals(productDto.getCountOfCalories(), product.getCountOfCalories());
        Assertions.assertEquals(0.0, product.getProtein());
        Assertions.assertEquals(0.0, product.getFat());
        Assertions.assertNull(product.getCarbohydrate());
    }
}