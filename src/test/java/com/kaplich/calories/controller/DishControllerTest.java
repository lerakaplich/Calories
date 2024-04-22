package com.kaplich.calories.controller;

import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DishControllerTest {

    @Mock
    private DishService dishService;

    private DishController dishController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dishController = new DishController(dishService);
    }

    @Test
    void testFindAllDishes() {
        List<DishDto> expectedDishes = new ArrayList<>();
        when(dishService.findAllDishes()).thenReturn(expectedDishes);

        List<DishDto> result = dishController.findAllDishes();

        assertEquals(expectedDishes, result);
        verify(dishService, times(1)).findAllDishes();
    }

    @Test
    void testSaveDish() {
        DishDto dishDto = new DishDto();
        DishDto savedDishDto = new DishDto();
        when(dishService.saveDish(dishDto)).thenReturn(savedDishDto);

        DishDto result = dishController.saveDish(dishDto);

        assertEquals(savedDishDto, result);
        verify(dishService, times(1)).saveDish(dishDto);
    }

    @Test
    void testFindByDishName() {
        String dishName = "Pizza";
        DishDto expectedDishDto = new DishDto();
        when(dishService.findByDishName(dishName)).thenReturn(expectedDishDto);

        DishDto result = dishController.findByDishName(dishName);

        assertEquals(expectedDishDto, result);
        verify(dishService, times(1)).findByDishName(dishName);
    }

    @Test
    void testUpdateDish() {
        String dishName = "Pizza";
        String newDishName = "Pasta";
        Dish updatedDish = new Dish();
        when(dishService.updateDish(dishName, newDishName)).thenReturn(updatedDish);

        Dish result = dishController.updateDish(dishName, newDishName);

        assertEquals(updatedDish, result);
        verify(dishService, times(1)).updateDish(dishName, newDishName);
    }

    @Test
    void testDeleteDish() {
        String dishName = "Pizza";

        dishController.deleteDish(dishName);

        verify(dishService, times(1)).deleteDish(dishName);
    }

    @Test
    void testFindByClientNameAndCountOfCalories() {
        String clientName = "John Doe";
        double countOfCalories = 500.0;
        List<DishDto> expectedDishes = new ArrayList<>();
        when(dishService.findByClientNameAndCountOfCalories(clientName, countOfCalories)).thenReturn(expectedDishes);

        List<DishDto> result = dishController.findByClientNameAndCountOfCalories(clientName, countOfCalories);

        assertEquals(expectedDishes, result);
        verify(dishService, times(1)).findByClientNameAndCountOfCalories(clientName, countOfCalories);
    }
}