package com.kaplich.calories.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductTest {

    private Product product;
    private List<Dish> dishList;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);
        product.setProductName("Cheese");
        product.setCountOfCalories(100);
        product.setProtein(5.0);
        product.setFat(10.0);
        product.setCarbohydrate(2.0);

        // Создаем список блюд
        dishList = new ArrayList<>();
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setDishName("Pizza");

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setDishName("Burger");

        dishList.add(dish1);
        dishList.add(dish2);

        product.setDishList(dishList);
    }

    @Test
    public void testGettersAndSetters() {
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals("Cheese", product.getProductName());
        Assertions.assertEquals(100, product.getCountOfCalories());
        Assertions.assertEquals(5.0, product.getProtein());
        Assertions.assertEquals(10.0, product.getFat());
        Assertions.assertEquals(2.0, product.getCarbohydrate());
        Assertions.assertEquals(dishList, product.getDishList());
    }

    @Test
    public void testCalculateTotalDishes() {
        int expectedTotalDishes = 2;

        int actualTotalDishes = product.getDishList().size();

        Assertions.assertEquals(expectedTotalDishes, actualTotalDishes);
    }
}