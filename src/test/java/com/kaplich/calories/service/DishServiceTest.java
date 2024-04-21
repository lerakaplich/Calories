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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        verify(dishRepository, times(1)).findAll();
        verifyNoMoreInteractions(dishRepository);
        verify(dishCache, times(1)).put("all", result);
        verifyNoMoreInteractions(dishCache);
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
        verify(dishRepository, times(1)).findByDishName(anyString());
        verify(dishRepository, times(1)).save(any(Dish.class));
        verify(dishCache, times(1)).put("all", dishDto);
        verifyNoMoreInteractions(dishRepository);
        verifyNoMoreInteractions(dishCache);
    }

    @Test
    public void testFindByDishName() {
        String dishName = "Test Dish";
        Dish dish = new Dish();
        dish.setDishName(dishName);

        when(dishRepository.findByDishName(dishName)).thenReturn(dish);

        DishDto result = dishService.findByDishName(dishName);

        assertEquals(DishMapper.toDto(dish), result);
        verify(dishRepository, times(1)).findByDishName(dishName);
        verifyNoMoreInteractions(dishRepository);
        verifyNoMoreInteractions(clientCache);
    }

    // Дополнительные тесты для остальных методов класса DishService

}