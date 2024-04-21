package com.kaplich.calories.mapper;

import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.mapper.ClientMapper;
import com.kaplich.calories.mapper.DishMapper;
import com.kaplich.calories.mapper.ProductMapper;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DishMapperTest {

    @Test
    void testToDto() {
        // Arrange
        Dish dish = new Dish();
        dish.setDishName("Chicken Salad");
        dish.setCountOfCalories(250.0);

        Product product1 = new Product();
        product1.setProductName("Chicken");
        product1.setCountOfCalories(200.0);
        Product product2 = new Product();
        product2.setProductName("Lettuce");
        product2.setCountOfCalories(50.0);
        List<Product> productList = Arrays.asList(product1, product2);
        dish.setProductList(productList);

        // Act
        DishDto dishDto = DishMapper.toDto(dish);

        // Assert
        Assertions.assertEquals(dish.getDishName(), dishDto.getDishName());
        Assertions.assertEquals(dish.getCountOfCalories(), dishDto.getCountOfCalories());
        Assertions.assertEquals(2, dishDto.getProductList().size());
        Assertions.assertEquals("Chicken", dishDto.getProductList().get(0).getProductName());
        Assertions.assertEquals(200.0, dishDto.getProductList().get(0).getCountOfCalories());
        Assertions.assertEquals("Lettuce", dishDto.getProductList().get(1).getProductName());
        Assertions.assertEquals(50.0, dishDto.getProductList().get(1).getCountOfCalories());
    }

    @Test
    void testToDtoWithNullDish() {
        // Act
        DishDto dishDto = DishMapper.toDto(null);

        // Assert
        Assertions.assertNull(dishDto);
    }

    @Test
    void testToEntity() {
        // Arrange
        DishDto dishDto = DishDto.builder()
                .dishName("Pasta")
                .countOfCalories(400.0)
                .clientDto(null) // Not testing client mapping in this test
                .productList(Arrays.asList(
                        ProductDto.builder().productName("Pasta").countOfCalories(300.0).build(),
                        ProductDto.builder().productName("Tomato Sauce").countOfCalories(100.0).build()
                ))
                .build();

        // Act
        Dish dish = DishMapper.toEntity(dishDto);

        // Assert
        Assertions.assertEquals(dishDto.getDishName(), dish.getDishName());
        Assertions.assertEquals(dishDto.getCountOfCalories(), dish.getCountOfCalories());
        Assertions.assertEquals(2, dish.getProductList().size());
        Assertions.assertEquals("Pasta", dish.getProductList().get(0).getProductName());
        Assertions.assertEquals(300.0, dish.getProductList().get(0).getCountOfCalories());
        Assertions.assertEquals("Tomato Sauce", dish.getProductList().get(1).getProductName());
        Assertions.assertEquals(100.0, dish.getProductList().get(1).getCountOfCalories());
    }

    @Test
    void testToEntityWithNullDishDto() {
        // Act
        Dish dish = DishMapper.toEntity(null);

        // Assert
        Assertions.assertNull(dish);
    }
}