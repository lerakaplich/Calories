package com.kaplich.calories.controller;

import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/dishes")
public class DishController {
    private final DishService service;

    @GetMapping
    public List<DishDto> findAllDishes() {
        return service.findAllDishes();
    }

    @PostMapping("/save")
    public DishDto saveDish(@RequestBody final DishDto dishDto) {
        return service.saveDish(dishDto);
    }

    @GetMapping("/findByName")
    public DishDto findByDishName(@RequestParam final String nameOfDish) {

        return service.findByDishName(nameOfDish);
    }

    @PutMapping("/update")
    public Dish updateDish(final String dishName, final String newDishName) {
        return service.updateDish(dishName, newDishName);
    }

    @DeleteMapping("/delete")
    public void deleteDish(@RequestParam final String nameOfDish) {

        service.deleteDish(nameOfDish);
    }

    @GetMapping("/findByNameAndCountOfCalories")
    public List<DishDto> findByClientNameAndCountOfCalories(
            @Param("clientName") final String clientName,
            @Param("countOfCalories") final double countOfCalories) {
        return service.
                findByClientNameAndCountOfCalories(clientName, countOfCalories);
    }
}
