package com.kaplich.calories.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DishTest {

    private Dish dish;
    private List<Product> productList;

    @BeforeEach
    public void setup() {
        dish = new Dish();
        dish.setId(1L);
        dish.setDishName("Pizza");
        dish.setCountOfCalories(1200);

        // Создаем список продуктов
        productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductName("Flour");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductName("Cheese");

        productList.add(product1);
        productList.add(product2);

        dish.setProductList(productList);
    }

    @Test
    public void testGettersAndSetters() {
        Assertions.assertEquals(1L, dish.getId());
        Assertions.assertEquals("Pizza", dish.getDishName());
        Assertions.assertEquals(1200, dish.getCountOfCalories());
        Assertions.assertEquals(productList, dish.getProductList());
    }

    @Test
    public void testCalculateTotalProducts() {
        int expectedTotalProducts = 2;

        int actualTotalProducts = dish.getProductList().size();

        Assertions.assertEquals(expectedTotalProducts, actualTotalProducts);
    }
}