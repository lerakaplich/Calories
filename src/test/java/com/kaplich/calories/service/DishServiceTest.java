package com.kaplich.calories.service;

import com.kaplich.calories.cache.CacheEntity;
import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.mapper.DishMapper;
import com.kaplich.calories.mapper.ProductMapper;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.repository.DishRepository;
import com.kaplich.calories.repository.ProductRepository;
import com.kaplich.calories.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    DishRepository dishRepository;

    @Mock
    CacheEntity dishCache;

    @Mock
    CacheEntity clientCache;

    @InjectMocks
    DishService dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllDishes() {
        Dish dish1 = new Dish();
        Dish dish2 = new Dish();
        List<Dish> dishList = new ArrayList<>();
        dishList.add(dish1);
        dishList.add(dish2);

        when(dishRepository.findAll()).thenReturn(dishList);

        List<DishDto> result = dishService.findAllDishes();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateDish_ExistingDish() {
        String dishName = "TestDish";
        String newDishName = "NewTestDish";

        Dish existingDish = new Dish();
        existingDish.setDishName(dishName);
        when(dishRepository.findByDishName(dishName)).thenReturn(existingDish);

        Dish updatedDish = dishService.updateDish(dishName, newDishName);

        assertNotNull(updatedDish);
        assertEquals(newDishName, updatedDish.getDishName());

    }

    @Test
    void testUpdateDish_NonExistingDish() {
        String dishName = "NonExistingDish";
        String newDishName = "NewTestDish";

        when(dishRepository.findByDishName(dishName)).thenReturn(null);

        Dish updatedDish = dishService.updateDish(dishName, newDishName);

        assertNull(updatedDish);

        verify(dishRepository, times(1)).findByDishName(dishName);
        verify(dishCache, never()).put(anyString(), any(DishDto.class));
    }

    @Test
    void testDeleteDish_ExistingDish() {
        String dishName = "TestDish";

        Dish existingDish = new Dish();
        existingDish.setDishName(dishName);
        when(dishRepository.findByDishName(dishName)).thenReturn(existingDish);

        dishService.deleteDish(dishName);

    }

    @Test
    void testDeleteDish_NonExistingDish() {
        String dishName = "NonExistingDish";

        when(dishRepository.findByDishName(dishName)).thenReturn(null);

        dishService.deleteDish(dishName);

        verify(dishRepository, times(1)).findByDishName(dishName);
        verify(dishRepository, never()).delete(any(Dish.class));
        verify(dishCache, never()).remove(anyString());
    }

    @Test
    void testFindByClientNameAndCountOfCalories_CacheHitWithValidList() {
        String clientName = "TestClient";
        double countOfCalories = 100.0;

        String cacheKey = clientName + "_" + countOfCalories;
        List<DishDto> cachedList = new ArrayList<>();
        cachedList.add(new DishDto());
        when(dishCache.get(cacheKey)).thenReturn(cachedList);

        List<DishDto> dishDtoList = dishService.findByClientNameAndCountOfCalories(clientName, countOfCalories);

        assertNotNull(dishDtoList);
        assertFalse(dishDtoList.isEmpty());

        verify(dishRepository, never()).findByClientNameAndCountOfCalories(anyString(), anyDouble());
        verify(dishCache, never()).put(anyString(), any(List.class));
    }

    @Test
    void testFindByClientNameAndCountOfCalories_CacheMiss() {
        String clientName = "TestClient";
        double countOfCalories = 100.0;

        String cacheKey = clientName + "_" + countOfCalories;
        when(dishCache.get(cacheKey)).thenReturn(null);

        List<Dish> dishList = new ArrayList<>();
        dishList.add(new Dish());
        when(dishRepository.findByClientNameAndCountOfCalories(clientName, countOfCalories)).thenReturn(dishList);

        List<DishDto> dishDtoList = dishService.findByClientNameAndCountOfCalories(clientName, countOfCalories);

        assertNotNull(dishDtoList);
        assertFalse(dishDtoList.isEmpty());

    }


    @Test
    void testSaveDish() {
        DishDto dishDto = new DishDto();
        dishDto.setDishName("Test Dish");
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Test Product");
        dishDto.setProductList(List.of(productDto));

        when(dishRepository.findByDishName(anyString())).thenReturn(null);
        when(dishRepository.save(any(Dish.class))).thenReturn(new Dish());

        DishDto result = dishService.saveDish(dishDto);

        assertEquals(dishDto, result);

    }

    @Test
    void testFindByDishName() {
        String dishName = "Test Dish";
        Dish dish = new Dish();
        dish.setDishName(dishName);

        when(dishRepository.findByDishName(dishName)).thenReturn(dish);
        DishDto result = dishService.findByDishName(dishName);
    }

    // Дополнительные тесты для остальных методов класса DishService

}