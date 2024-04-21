package com.kaplich.calories.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ClientTest {

    private Client client;
    private List<Dish> dishList;

    @BeforeEach
    void setup() {
        client = new Client();
        client.setId(1L);
        client.setClientName("John Doe");
        client.setWeight(70.5);
        client.setHeight(175.0);

        // Создаем список блюд
        dishList = new ArrayList<>();
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setDishName("Pizza");
        dish1.setCountOfCalories(1200);

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setDishName("Salad");
        dish2.setCountOfCalories(200);

        dishList.add(dish1);
        dishList.add(dish2);

        client.setDishList(dishList);
    }

    @Test
    void testGettersAndSetters() {
        Assertions.assertEquals(1L, client.getId());
        Assertions.assertEquals("John Doe", client.getClientName());
        Assertions.assertEquals(70.5, client.getWeight());
        Assertions.assertEquals(175.0, client.getHeight());
        Assertions.assertEquals(dishList, client.getDishList());
    }

    @Test
    void testCalculateTotalCalories() {
        int expectedTotalCalories = 1400; // 1200 + 200

        int actualTotalCalories = 0;
        List<Dish> dishList = client.getDishList();
        for (Dish dish : dishList) {
            actualTotalCalories += dish.getCountOfCalories();
        }
        Assertions.assertEquals(expectedTotalCalories, actualTotalCalories);
    }
}
